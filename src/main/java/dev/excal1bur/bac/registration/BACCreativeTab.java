package dev.excal1bur.bac.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.util.AEColor;
import dev.excal1bur.bac.BetterAECables;
import dev.excal1bur.bac.tier.BacTiers;
import dev.excal1bur.bac.tier.CableShape;

public final class BACCreativeTab {
    private BACCreativeTab() {
    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            BetterAECables.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bac"))
                    .icon(() -> BACParts.get(BacTiers.HYPERCONDUCTIVE, CableShape.SMART_DENSE, AEColor.TRANSPARENT)
                            .get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (var tier : BacTiers.ALL) {
                            for (var shape : tier.shapes()) {
                                for (var color : AEColor.values()) {
                                    var holder = BACParts.get(tier, shape, color);
                                    if (holder != null) {
                                        output.accept(holder.get());
                                    }
                                }
                            }
                        }
                    })
                    .build());
}
