package dev.sixteenbitforge.aeoc.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.util.AEColor;
import dev.sixteenbitforge.aeoc.AEOverclockedCables;
import dev.sixteenbitforge.aeoc.tier.AeocTiers;
import dev.sixteenbitforge.aeoc.tier.CableShape;

public final class AEOCCreativeTab {
    private AEOCCreativeTab() {
    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            AEOverclockedCables.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.aeoc"))
                    .icon(() -> AEOCParts.get(AeocTiers.HYPERCONDUCTIVE, CableShape.SMART_DENSE, AEColor.TRANSPARENT)
                            .get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (var tier : AeocTiers.ALL) {
                            for (var shape : tier.shapes()) {
                                for (var color : AEColor.values()) {
                                    var holder = AEOCParts.get(tier, shape, color);
                                    if (holder != null) {
                                        output.accept(holder.get());
                                    }
                                }
                            }
                        }
                    })
                    .build());
}
