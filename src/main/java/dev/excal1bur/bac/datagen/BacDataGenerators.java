package dev.excal1bur.bac.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import dev.excal1bur.bac.BetterAECables;

@EventBusSubscriber(modid = BetterAECables.MOD_ID)
public class BacDataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var pack = generator.getVanillaPack(true);

        pack.addProvider(BacLanguageProvider::new);
        pack.addProvider(BacItemModelProvider::new);
        pack.addProvider(output -> new BacRecipeProvider.Runner(output, event.getLookupProvider()));
    }
}
