package dev.excal1bur.bac.tier;

import java.util.function.BiFunction;

import appeng.api.parts.IPartItem;
import appeng.core.definitions.AEParts;
import appeng.core.definitions.ColoredItemDefinition;
import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.CablePart;
import dev.excal1bur.bac.parts.TieredCoveredCablePart;
import dev.excal1bur.bac.parts.TieredCoveredDenseCablePart;
import dev.excal1bur.bac.parts.TieredGlassCablePart;
import dev.excal1bur.bac.parts.TieredSmartCablePart;
import dev.excal1bur.bac.parts.TieredSmartDenseCablePart;

/**
 * Mirrors AE2's own five {@code AECableType} shapes (glass/covered/smart, each normal or dense -- AE2 has no dense
 * glass cable, so there are five, not six). Each new tier we add (see {@link CableTier}) re-creates a colored item
 * family for whichever of these shapes it supports.
 */
public enum CableShape {
    GLASS("glass_cable", "Glass Cable", false, AEParts.GLASS_CABLE, TieredGlassCablePart.class,
            TieredGlassCablePart::new),
    COVERED("covered_cable", "Covered Cable", false, AEParts.COVERED_CABLE, TieredCoveredCablePart.class,
            TieredCoveredCablePart::new),
    SMART("smart_cable", "Smart Cable", false, AEParts.SMART_CABLE, TieredSmartCablePart.class,
            TieredSmartCablePart::new),
    COVERED_DENSE("covered_dense_cable", "Dense Covered Cable", true, AEParts.COVERED_DENSE_CABLE,
            TieredCoveredDenseCablePart.class, TieredCoveredDenseCablePart::new),
    /** Named just "Dense Cable" (not "Smart Dense Cable") to match the brief's "Overclocked Dense Cable" naming. */
    SMART_DENSE("smart_dense_cable", "Dense Cable", true, AEParts.SMART_DENSE_CABLE, TieredSmartDenseCablePart.class,
            TieredSmartDenseCablePart::new);

    private final String idSuffix;
    private final String nameSuffix;
    private final boolean dense;
    private final ColoredItemDefinition<? extends IPartItem<?>> baseTierItem;
    private final Class<? extends CablePart> partClass;
    private final BiFunction<ColoredPartItem<?>, Integer, ? extends CablePart> factory;

    CableShape(String idSuffix, String nameSuffix, boolean dense,
            ColoredItemDefinition<? extends IPartItem<?>> baseTierItem, Class<? extends CablePart> partClass,
            BiFunction<ColoredPartItem<?>, Integer, ? extends CablePart> factory) {
        this.idSuffix = idSuffix;
        this.nameSuffix = nameSuffix;
        this.dense = dense;
        this.baseTierItem = baseTierItem;
        this.partClass = partClass;
        this.factory = factory;
    }

    public String idSuffix() {
        return idSuffix;
    }

    public String nameSuffix() {
        return nameSuffix;
    }

    public boolean isDense() {
        return dense;
    }

    /** The vanilla AE2 tier-1 item family this shape corresponds to (used as the base upgrade recipe ingredient). */
    public ColoredItemDefinition<? extends IPartItem<?>> baseTierItem() {
        return baseTierItem;
    }

    public Class<? extends CablePart> partClass() {
        return partClass;
    }

    public BiFunction<ColoredPartItem<?>, Integer, ? extends CablePart> factory() {
        return factory;
    }
}
