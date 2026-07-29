package dev.excal1bur.bac.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;

import appeng.api.util.AEColor;
import dev.excal1bur.bac.registration.BACParts;
import dev.excal1bur.bac.tier.BacTiers;
import dev.excal1bur.bac.tier.CableShape;
import dev.excal1bur.bac.tier.CableTier;

/**
 * One shapeless upgrade recipe per registered cable variant: the cable it upgrades from (either the matching vanilla
 * AE2 tier-1 cable, or the matching cable from a prior {@link CableTier} -- see {@link CableTier#upgradeFrom()}),
 * plus that tier's flat extra material cost.
 */
public class BacRecipeProvider extends RecipeProvider {
    public BacRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (var tier : BacTiers.ALL) {
            for (var shape : tier.shapes()) {
                for (var color : AEColor.values()) {
                    buildRecipe(tier, shape, color);
                }
            }
        }
    }

    private void buildRecipe(CableTier tier, CableShape shape, AEColor color) {
        var result = BACParts.get(tier, shape, color);
        if (result == null) {
            return;
        }

        ItemLike baseIngredient = resolveBaseIngredient(tier, shape, color);
        if (baseIngredient == null) {
            // The tier we upgrade from doesn't offer this shape (not currently possible, but shapes are
            // per-tier configurable -- see BacTiers.HYPERCONDUCTIVE), so there's nothing to build a recipe from.
            return;
        }

        var builder = shapeless(RecipeCategory.MISC, result.get())
                .requires(baseIngredient);
        for (var extra : tier.extraIngredients()) {
            builder.requires(extra);
        }
        builder.unlockedBy("has_base_cable", has(baseIngredient))
                .save(output);
    }

    private ItemLike resolveBaseIngredient(CableTier tier, CableShape shape, AEColor color) {
        var upgradeFrom = tier.upgradeFrom();
        if (upgradeFrom == null) {
            return shape.baseTierItem().item(color);
        }
        var holder = BACParts.get(upgradeFrom, shape, color);
        return holder == null ? null : holder.get();
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new BacRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "BAC Recipes";
        }
    }
}
