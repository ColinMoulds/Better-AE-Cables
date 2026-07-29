package dev.excal1bur.bac.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.CoveredCablePart;
import dev.excal1bur.bac.api.OverclockedCableService;

/** {@link CoveredCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredCoveredCablePart extends CoveredCablePart {
    public TieredCoveredCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
