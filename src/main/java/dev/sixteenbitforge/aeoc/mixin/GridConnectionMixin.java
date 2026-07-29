package dev.sixteenbitforge.aeoc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.api.networking.IGridNode;
import appeng.api.networking.pathing.ChannelMode;
import appeng.me.GridConnection;
import appeng.me.GridNode;
import dev.sixteenbitforge.aeoc.api.OverclockedCableService;

/**
 * Mirrors {@link GridNodeMixin} for {@link GridConnection#getMaxChannels()}, which independently hardcodes
 * {@code 32 * mode.getCableCapacityFactor()}. As of AE2 26.1.10-beta this particular method isn't actually consulted
 * by the live pathing bottleneck check (that walks {@code GridNode#getHighestSimilarAncestor()} chains and calls
 * {@code GridNode#getMaxChannels()} directly) -- but it's still public-ish surface (used for display/other callers
 * historically) so we patch it too for consistency and forward-compatibility.
 */
@Mixin(GridConnection.class)
public abstract class GridConnectionMixin {

    @Shadow
    public abstract GridNode a();

    @Shadow
    public abstract GridNode b();

    @Inject(method = "getMaxChannels", at = @At("HEAD"), cancellable = true)
    private void aeoc$getMaxChannels(CallbackInfoReturnable<Integer> cir) {
        var service = aeoc$serviceOf(a());
        if (service == null) {
            service = aeoc$serviceOf(b());
        }
        if (service == null) {
            return;
        }

        var channelMode = b().getGrid().getPathingService().getChannelMode();
        cir.setReturnValue(OverclockedCableService.resolveMaxChannels(service, channelMode));
    }

    private static OverclockedCableService aeoc$serviceOf(IGridNode node) {
        return node.getService(OverclockedCableService.class);
    }
}
