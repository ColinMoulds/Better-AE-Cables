package dev.excal1bur.aeoc.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import appeng.api.util.AEColor;
import dev.excal1bur.aeoc.BetterAECables;
import dev.excal1bur.aeoc.registration.AEOCParts;
import dev.excal1bur.aeoc.tier.AeocTiers;

public class AeocLanguageProvider extends LanguageProvider {
    public AeocLanguageProvider(PackOutput output) {
        super(output, BetterAECables.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.aeoc", "Better AE Cables");

        for (var tier : AeocTiers.ALL) {
            for (var shape : tier.shapes()) {
                for (var color : AEColor.values()) {
                    var holder = AEOCParts.get(tier, shape, color);
                    if (holder == null) {
                        continue;
                    }
                    add(holder.get(), color.englishName + " " + tier.displayName(shape));
                }
            }
        }
    }
}
