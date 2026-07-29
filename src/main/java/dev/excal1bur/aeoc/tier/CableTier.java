package dev.excal1bur.aeoc.tier;

import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.ItemLike;

/**
 * A new cable tier this mod adds (e.g. "Overclocked", "Hyperconductive"): which of the five {@link CableShape}s it
 * offers, the base channel capacities it grants, and what it's crafted from.
 */
public record CableTier(
        String id,
        String displayPrefix,
        Set<CableShape> shapes,
        int normalCapacity,
        int denseCapacity,
        /** Null means "upgrades from the matching vanilla AE2 tier-1 cable"; see {@link CableShape#baseTierItem()}. */
        @Nullable CableTier upgradeFrom,
        /** Extra materials required alongside the cable being upgraded, one of each. */
        List<ItemLike> extraIngredients) {

    public int capacityFor(CableShape shape) {
        return shape.isDense() ? denseCapacity : normalCapacity;
    }

    public String itemIdSuffix(CableShape shape) {
        return id + "_" + shape.idSuffix();
    }

    public String displayName(CableShape shape) {
        return displayPrefix + " " + shape.nameSuffix();
    }
}
