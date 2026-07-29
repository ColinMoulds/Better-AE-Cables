package dev.excal1bur.bac.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.SmartCablePart;
import dev.excal1bur.bac.api.OverclockedCableService;

/** {@link SmartCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredSmartCablePart extends SmartCablePart {
    public TieredSmartCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
