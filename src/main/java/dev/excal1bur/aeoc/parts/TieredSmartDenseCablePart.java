package dev.excal1bur.aeoc.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.SmartDenseCablePart;
import dev.excal1bur.aeoc.api.OverclockedCableService;

/** {@link SmartDenseCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredSmartDenseCablePart extends SmartDenseCablePart {
    public TieredSmartDenseCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
