package dev.excal1bur.aeoc.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import dev.excal1bur.aeoc.BetterAECables;

@EventBusSubscriber(modid = BetterAECables.MOD_ID)
public class AeocDataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var pack = generator.getVanillaPack(true);

        pack.addProvider(AeocLanguageProvider::new);
        pack.addProvider(AeocItemModelProvider::new);
        pack.addProvider(output -> new AeocRecipeProvider.Runner(output, event.getLookupProvider()));
    }
}
