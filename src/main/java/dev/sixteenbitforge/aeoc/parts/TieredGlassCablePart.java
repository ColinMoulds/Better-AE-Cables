package dev.sixteenbitforge.aeoc.parts;

import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.GlassCablePart;
import dev.sixteenbitforge.aeoc.api.OverclockedCableService;

/** {@link GlassCablePart}, upgraded to report a tier-specific channel capacity. */
public class TieredGlassCablePart extends GlassCablePart {
    public TieredGlassCablePart(ColoredPartItem<?> partItem, int baseChannelCapacity) {
        super(partItem);
        getMainNode().addService(OverclockedCableService.class, OverclockedCableService.of(baseChannelCapacity));
    }
}
