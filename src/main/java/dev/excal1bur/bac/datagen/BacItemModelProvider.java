package dev.excal1bur.bac.datagen;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import appeng.api.util.AEColor;
import dev.excal1bur.bac.registration.BACParts;
import dev.excal1bur.bac.tier.BacTiers;
import dev.excal1bur.bac.tier.CableShape;

/**
 * Generates a minimal inventory-icon item model per registered cable variant, pointing at AE2's own existing cable
 * textures -- there's already one for every shape x color combination, since AE2 needs them for its own tier-1
 * items, and the texture path is keyed by shape+color, not by mod/item id. No new texture assets needed.
 * <p>
 * In-world rendering doesn't go through per-item models at all: AE2's {@code CableBusModel} picks the cable mesh and
 * texture purely from {@code AECableType} + {@code AEColor}, both of which our {@code Tiered*CablePart}s inherit
 * unchanged from their AE2 base classes. So a Tiered Smart Cable already renders exactly like a normal Smart Cable
 * with zero extra work here -- only the inventory icon (this class) needed anything at all.
 * <p>
 * As of this Minecraft version, items no longer bind to a model purely by the {@code models/item/<id>.json} naming
 * convention -- that file is now just a referenceable model, and a separate {@code assets/<ns>/items/<id>.json}
 * "item definition" (added by Mojang's item/model rework) is what actually binds an item to it. Without that
 * binding file the item silently gets no model at all (missing-texture icon). This provider writes both.
 */
public class BacItemModelProvider implements DataProvider {
    private static final String AE2_NAMESPACE = "ae2";

    private final PackOutput.PathProvider modelPathProvider;
    private final PackOutput.PathProvider itemDefinitionPathProvider;

    public BacItemModelProvider(PackOutput output) {
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
        this.itemDefinitionPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        var futures = new ArrayList<CompletableFuture<?>>();

        for (var tier : BacTiers.ALL) {
            for (var shape : tier.shapes()) {
                for (var color : AEColor.values()) {
                    var holder = BACParts.get(tier, shape, color);
                    if (holder == null) {
                        continue;
                    }
                    var itemId = holder.getId();
                    var modelId = Identifier.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemId.getPath());

                    var model = new JsonObject();
                    model.addProperty("parent", "minecraft:item/generated");
                    var textures = new JsonObject();
                    textures.addProperty("layer0", bacTextureFor(shape, color).toString());
                    model.add("textures", textures);
                    futures.add(DataProvider.saveStable(cachedOutput, model, modelPathProvider.json(itemId)));

                    var itemDefinition = new JsonObject();
                    var modelRef = new JsonObject();
                    modelRef.addProperty("type", "minecraft:model");
                    modelRef.addProperty("model", modelId.toString());
                    itemDefinition.add("model", modelRef);
                    futures.add(DataProvider.saveStable(cachedOutput, itemDefinition,
                            itemDefinitionPathProvider.json(itemId)));
                }
            }
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private static Identifier bacTextureFor(CableShape shape, AEColor color) {
        return Identifier.fromNamespaceAndPath(AE2_NAMESPACE,
                "part/cable/" + textureDir(shape) + "/" + color.name().toLowerCase(Locale.ROOT));
    }

    private static String textureDir(CableShape shape) {
        return switch (shape) {
            case GLASS -> "glass";
            case COVERED -> "covered";
            case SMART -> "smart";
            case COVERED_DENSE -> "dense_covered";
            case SMART_DENSE -> "dense_smart";
        };
    }

    @Override
    public String getName() {
        return "BAC Item Models";
    }
}
