package dev.sixteenbitforge.aeoc.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import appeng.api.util.AEColor;
import dev.sixteenbitforge.aeoc.AEOverclockedCables;
import dev.sixteenbitforge.aeoc.registration.AEOCParts;
import dev.sixteenbitforge.aeoc.tier.AeocTiers;

public class AeocLanguageProvider extends LanguageProvider {
    public AeocLanguageProvider(PackOutput output) {
        super(output, AEOverclockedCables.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.aeoc", "AE2 Overclocked Cables");

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
