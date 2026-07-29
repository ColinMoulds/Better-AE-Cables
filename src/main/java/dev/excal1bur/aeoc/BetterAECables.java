package dev.excal1bur.aeoc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.excal1bur.aeoc.config.AeocConfig;
import dev.excal1bur.aeoc.registration.AEOCCreativeTab;
import dev.excal1bur.aeoc.registration.AEOCParts;

@Mod(BetterAECables.MOD_ID)
public class BetterAECables {
    public static final String MOD_ID = "aeoc";
    private static final Logger LOG = LoggerFactory.getLogger(BetterAECables.class);

    public BetterAECables(IEventBus modEventBus, ModContainer modContainer) {
        AEOCParts.ITEMS.register(modEventBus);
        AEOCCreativeTab.TABS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, AeocConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOG.info("Better AE Cables loading");
    }
}
