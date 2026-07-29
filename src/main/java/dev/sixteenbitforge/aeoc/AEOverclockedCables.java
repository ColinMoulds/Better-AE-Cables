package dev.sixteenbitforge.aeoc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.sixteenbitforge.aeoc.config.AeocConfig;
import dev.sixteenbitforge.aeoc.registration.AEOCParts;

@Mod(AEOverclockedCables.MOD_ID)
public class AEOverclockedCables {
    public static final String MOD_ID = "aeoc";
    private static final Logger LOG = LoggerFactory.getLogger(AEOverclockedCables.class);

    public AEOverclockedCables(IEventBus modEventBus, ModContainer modContainer) {
        AEOCParts.ITEMS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, AeocConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOG.info("AE2 Overclocked Cables loading");
    }
}
