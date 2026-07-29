package dev.excal1bur.aeoc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.api.networking.IGridNode;
import appeng.api.networking.pathing.ChannelMode;
import appeng.me.GridNode;
import dev.excal1bur.aeoc.api.OverclockedCableService;

/**
 * Patches {@link GridNode#getMaxChannels()}, the true source of truth for a grid node's channel capacity (see
 * appeng.api.networking.GridFlags#DENSE_CAPACITY -- it only recognizes 8x/32x, gated by that single closed-enum
 * flag). If the node carries an {@link OverclockedCableService} (attached by our own cable parts via the public
 * {@code IManagedGridNode#addService} extension point), we substitute that tier's base capacity instead of AE2's
 * hardcoded 8/32, while still respecting CANNOT_CARRY and the global {@link ChannelMode} multiplier exactly like
 * vanilla does. Cables without the service (i.e. every vanilla AE2 cable) are completely unaffected.
 */
@Mixin(GridNode.class)
public abstract class GridNodeMixin {

    @Inject(method = "getMaxChannels", at = @At("HEAD"), cancellable = true)
    private void aeoc$getMaxChannels(CallbackInfoReturnable<Integer> cir) {
        var node = (IGridNode) this;

        var service = node.getService(OverclockedCableService.class);
        if (service == null) {
            return;
        }

        if (node.hasFlag(appeng.api.networking.GridFlags.CANNOT_CARRY)) {
            return;
        }

        var channelMode = node.getGrid().getPathingService().getChannelMode();
        cir.setReturnValue(OverclockedCableService.resolveMaxChannels(service, channelMode));
    }
}
