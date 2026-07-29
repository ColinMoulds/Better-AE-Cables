package dev.excal1bur.bac.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import appeng.api.util.AEColor;
import dev.excal1bur.bac.BetterAECables;
import dev.excal1bur.bac.registration.BACParts;
import dev.excal1bur.bac.tier.BacTiers;

public class BacLanguageProvider extends LanguageProvider {
    public BacLanguageProvider(PackOutput output) {
        super(output, BetterAECables.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.bac", "Better AE Cables");

        for (var tier : BacTiers.ALL) {
            for (var shape : tier.shapes()) {
                for (var color : AEColor.values()) {
                    var holder = BACParts.get(tier, shape, color);
                    if (holder == null) {
                        continue;
                    }
                    add(holder.get(), color.englishName + " " + tier.displayName(shape));
                }
            }
        }
    }
}
