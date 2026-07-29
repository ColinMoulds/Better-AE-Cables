package dev.sixteenbitforge.aeoc.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.util.AEColor;
import appeng.items.parts.ColoredPartItem;
import dev.sixteenbitforge.aeoc.AEOverclockedCables;
import dev.sixteenbitforge.aeoc.parts.OverclockedSmartCablePart;

/**
 * Prototype item registration. Deliberately a single, uncolored ("transparent") variant for now -- the full
 * color/covered/glass/dense/tier matrix is Step 3 (datagen-driven), not part of this prototype.
 */
public final class AEOCParts {
    private AEOCParts() {
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AEOverclockedCables.MOD_ID);

    public static final DeferredHolder<Item, ColoredPartItem<OverclockedSmartCablePart>> OVERCLOCKED_SMART_CABLE = ITEMS
            .register("overclocked_smart_cable",
                    id -> new ColoredPartItem<>(
                            new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)),
                            OverclockedSmartCablePart.class, OverclockedSmartCablePart::new, AEColor.TRANSPARENT));

    public static void init() {
        // Referencing this class triggers the static initializers above.
    }
}
