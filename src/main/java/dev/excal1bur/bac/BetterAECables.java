package dev.excal1bur.bac;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.excal1bur.bac.config.BacConfig;
import dev.excal1bur.bac.registration.BACCreativeTab;
import dev.excal1bur.bac.registration.BACParts;

@Mod(BetterAECables.MOD_ID)
public class BetterAECables {
    public static final String MOD_ID = "bac";
    private static final Logger LOG = LoggerFactory.getLogger(BetterAECables.class);

    public BetterAECables(IEventBus modEventBus, ModContainer modContainer) {
        BACParts.ITEMS.register(modEventBus);
        BACCreativeTab.TABS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, BacConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOG.info("Better AE Cables loading");
    }
}
