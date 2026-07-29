package dev.excal1bur.aeoc.tier;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.world.item.Items;

import appeng.core.definitions.AEItems;

/**
 * The two new cable tiers this mod adds. {@link #OVERCLOCKED} mirrors AE2's full five-shape cable family (glass,
 * covered, smart, covered-dense, smart-dense). {@link #HYPERCONDUCTIVE} is deliberately dense-only for now -- per
 * the brief it's crafted from the Overclocked *dense* cables specifically -- but nothing here prevents giving it the
 * full shape set in a later update, since {@link CableShape} and the registration loop don't assume a tier covers
 * every shape.
 */
public final class AeocTiers {
    private AeocTiers() {
    }

    public static final CableTier OVERCLOCKED = new CableTier(
            "overclocked",
            "Overclocked",
            EnumSet.allOf(CableShape.class),
            16,
            64,
            null,
            List.of(Items.REDSTONE, AEItems.CERTUS_QUARTZ_CRYSTAL));

    public static final CableTier HYPERCONDUCTIVE = new CableTier(
            "hyperconductive",
            "Hyperconductive",
            EnumSet.of(CableShape.COVERED_DENSE, CableShape.SMART_DENSE),
            -1, // no normal-capacity shapes in this tier (yet)
            128,
            OVERCLOCKED,
            List.of(Items.DIAMOND, AEItems.FLUIX_CRYSTAL));

    public static final List<CableTier> ALL = List.of(OVERCLOCKED, HYPERCONDUCTIVE);
}
