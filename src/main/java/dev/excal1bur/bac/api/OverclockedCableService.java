package dev.excal1bur.bac.api;

import appeng.api.networking.IGridNodeService;
import appeng.api.networking.pathing.ChannelMode;
import dev.excal1bur.bac.config.BacConfig;

/**
 * Attached to a grid node (via {@code IManagedGridNode#addService}) to mark it as belonging to one of this mod's
 * overclocked cable tiers, and to report the base channel capacity of that tier.
 * <p>
 * AE2's own {@code GridNode#getMaxChannels()} only recognizes two tiers (8 vs. 32, gated by the closed
 * {@code GridFlags#DENSE_CAPACITY} enum) and multiplies by the single, global {@code ChannelMode} factor. There is no
 * public extension point for additional numeric tiers, so {@code dev.excal1bur.bac.mixin} patches the handful
 * of internal methods that read that flag to also check for this service, keeping vanilla AE2 cables completely
 * unaffected.
 */
public interface OverclockedCableService extends IGridNodeService {
    /**
     * @return The base channel capacity this tier provides, before {@link BacConfig#CHANNEL_CAPACITY_MULTIPLIER} and
     *         the server's global {@code ChannelMode} cable capacity factor are applied (mirrors how vanilla applies
     *         that factor to its own 8/32 base values).
     */
    int getBaseChannelCapacity();

    static OverclockedCableService of(int baseChannelCapacity) {
        return () -> baseChannelCapacity;
    }

    /**
     * Resolves the final max-channel value for a node carrying this service, applying (in order) our own "unlimited"
     * override, AE2's own {@code ChannelMode.INFINITE}, then our capacity multiplier and AE2's cable capacity factor.
     * Shared by all three mixins so the logic only lives in one place.
     */
    static int resolveMaxChannels(OverclockedCableService service, ChannelMode channelMode) {
        if (BacConfig.UNLIMITED_CHANNELS.get() || channelMode == ChannelMode.INFINITE) {
            return Integer.MAX_VALUE;
        }
        var scaled = service.getBaseChannelCapacity() * BacConfig.CHANNEL_CAPACITY_MULTIPLIER.get();
        var base = (int) Math.max(1, Math.round(scaled));
        return base * channelMode.getCableCapacityFactor();
    }
}
