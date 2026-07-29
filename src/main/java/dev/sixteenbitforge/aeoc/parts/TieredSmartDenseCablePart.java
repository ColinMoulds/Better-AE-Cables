package dev.sixteenbitforge.aeoc.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.SmartDenseCablePart;
import dev.sixteenbitforge.aeoc.api.OverclockedCableService;

/** {@link SmartDenseCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredSmartDenseCablePart extends SmartDenseCablePart {
    public TieredSmartDenseCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
