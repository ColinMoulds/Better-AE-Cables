package dev.sixteenbitforge.aeoc.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.CoveredDenseCablePart;
import dev.sixteenbitforge.aeoc.api.OverclockedCableService;

/** {@link CoveredDenseCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredCoveredDenseCablePart extends CoveredDenseCablePart {
    public TieredCoveredDenseCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
