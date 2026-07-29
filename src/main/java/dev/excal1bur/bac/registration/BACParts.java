package dev.excal1bur.bac.registration;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.util.AEColor;
import appeng.items.parts.ColoredPartItem;
import appeng.parts.networking.CablePart;
import dev.excal1bur.bac.BetterAECables;
import dev.excal1bur.bac.tier.BacTiers;
import dev.excal1bur.bac.tier.CableShape;
import dev.excal1bur.bac.tier.CableTier;

/**
 * Registers one {@link ColoredPartItem} per (tier x shape x color) combination -- no hand-written per-variant
 * classes; the actual part classes are the five generic {@code Tiered*CablePart}s in {@code dev.excal1bur.bac.parts},
 * reused across every tier and color via {@link CableShape#factory()}.
 */
public final class BACParts {
    private BACParts() {
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterAECables.MOD_ID);

    private static final Map<CableTier, Map<CableShape, Map<AEColor, DeferredHolder<Item, ? extends ColoredPartItem<?>>>>> BY_TIER_SHAPE_COLOR = new LinkedHashMap<>();

    static {
        for (CableTier tier : BacTiers.ALL) {
            Map<CableShape, Map<AEColor, DeferredHolder<Item, ? extends ColoredPartItem<?>>>> byShape = new EnumMap<>(
                    CableShape.class);
            for (CableShape shape : tier.shapes()) {
                Map<AEColor, DeferredHolder<Item, ? extends ColoredPartItem<?>>> byColor = new EnumMap<>(
                        AEColor.class);
                for (AEColor color : AEColor.values()) {
                    byColor.put(color, registerCable(tier, shape, color));
                }
                byShape.put(shape, byColor);
            }
            BY_TIER_SHAPE_COLOR.put(tier, byShape);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends CablePart> DeferredHolder<Item, ColoredPartItem<T>> registerCable(CableTier tier,
            CableShape shape, AEColor color) {
        var id = color.registryPrefix + "_" + tier.itemIdSuffix(shape);
        var capacity = tier.capacityFor(shape);
        // Safe: partClass() and factory() always describe the same concrete Tiered*CablePart subtype for a given
        // CableShape constant, but that pairing isn't expressible without per-constant generic enum constants.
        var partClass = (Class<T>) shape.partClass();
        var factory = (BiFunction<ColoredPartItem<?>, Integer, T>) shape.factory();

        return ITEMS.register(id, itemId -> {
            var properties = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, itemId));
            return new ColoredPartItem<>(properties, partClass, item -> factory.apply(item, capacity), color);
        });
    }

    public static DeferredHolder<Item, ? extends ColoredPartItem<?>> get(CableTier tier, CableShape shape,
            AEColor color) {
        var byShape = BY_TIER_SHAPE_COLOR.get(tier);
        if (byShape == null) {
            return null;
        }
        var byColor = byShape.get(shape);
        return byColor == null ? null : byColor.get(color);
    }

    public static void init() {
        // Referencing this class triggers the static initializers above.
    }
}
