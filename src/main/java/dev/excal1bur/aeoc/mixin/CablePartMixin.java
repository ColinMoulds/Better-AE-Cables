package dev.excal1bur.aeoc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.api.implementations.parts.ICablePart;
import appeng.api.parts.IPart;
import appeng.parts.networking.CablePart;
import dev.excal1bur.aeoc.api.OverclockedCableService;
import dev.excal1bur.aeoc.config.AeocConfig;

/**
 * Patches the private {@code CablePart#getVisualChannels(int)} helper that drives the in-world channel-count
 * indicator (the little pips rendered on the cable).
 * <p>
 * Perhaps surprisingly, most of that method needs no change at all: it scales {@code channels} into whatever texture
 * range the shape's pip art supports (0-8 for normal cables, 0-32 for dense ones -- fixed by the existing art, and
 * unrelated to our actual capacity) using {@code node.getMaxChannels()} as the denominator, and
 * {@link GridNodeMixin} already patches that to our real capacity (16/64/128). So a normal cable at "8 out of our 16
 * capacity used" already renders as half-lit, correctly, with zero changes here.
 * <p>
 * The one gap: {@link AeocConfig#UNLIMITED_CHANNELS} is invisible to vanilla's own infinite-mode short-circuit
 * (which only checks AE2's global {@code ChannelMode}), so without this patch it would fall through to the normal
 * path and divide by {@code Integer.MAX_VALUE}, always rendering as empty. This replicates vanilla's own
 * infinite-mode behavior (full pips, or none if nothing is flowing) for that case.
 */
@Mixin(CablePart.class)
public abstract class CablePartMixin {

    @Inject(method = "getVisualChannels", at = @At("HEAD"), cancellable = true)
    private void aeoc$getVisualChannels(int channels, CallbackInfoReturnable<Byte> cir) {
        if (!AeocConfig.UNLIMITED_CHANNELS.get()) {
            return;
        }

        var node = ((IPart) this).getGridNode();
        if (node == null || node.getService(OverclockedCableService.class) == null) {
            return;
        }

        byte visualMaxChannels = switch (((ICablePart) this).getCableConnectionType()) {
            case NONE -> (byte) 0;
            case GLASS, SMART, COVERED -> (byte) 8;
            case DENSE_COVERED, DENSE_SMART -> (byte) 32;
        };
        cir.setReturnValue(channels <= 0 ? (byte) 0 : visualMaxChannels);
    }
}
