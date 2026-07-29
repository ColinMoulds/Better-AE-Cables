package dev.sixteenbitforge.aeoc.testplots;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEParts;
import appeng.server.testplots.TestPlot;
import appeng.server.testplots.TestPlotClass;
import appeng.server.testworld.PlotBuilder;

import dev.sixteenbitforge.aeoc.parts.OverclockedSmartCablePart;
import dev.sixteenbitforge.aeoc.registration.AEOCParts;

/**
 * Reuses AE2's own {@code @TestPlot}/GameTest infrastructure (it scans for {@code @TestPlotClass}-annotated classes
 * across ALL loaded mods, not just itself -- see {@code appeng.server.testplots.TestPlots#findAllTestPlotClasses}) to
 * exercise the Overclocked Smart Cable on a real, booted ME network with a controller, instead of requiring manual
 * in-game placement to check the Step 1/2 mixin work.
 * <p>
 * Only runs when AE2 is started with {@code -Dappeng.tests=true} (see {@code gameTestServer} run in build.gradle),
 * e.g. via {@code ./gradlew runGameTestServer}.
 */
@TestPlotClass
public final class AeocTestPlots {
    private AeocTestPlots() {
    }

    /**
     * Controller at x=[0,1], a 16-block run of Overclocked Smart Cable at x=[2,17] each carrying one ME Terminal
     * (each terminal requires exactly 1 channel). A normal Smart Cable caps at 8 channels, so this configuration can
     * only fully boot if the cable segment closest to the controller (x=2, which carries the combined trunk load of
     * all 16 terminals behind it) actually reports and enforces a capacity of 16.
     */
    @TestPlot("overclocked_smart_cable_16_channels")
    public static void overclockedSmartCable16Channels(PlotBuilder plot) {
        plot.creativeEnergyCell("0 -1 0");
        plot.block("[0,1] 0 0", AEBlocks.CONTROLLER);
        plot.cable("[2,17] 0 0", AEOCParts.OVERCLOCKED_SMART_CABLE.get())
                .part(Direction.UP, AEParts.TERMINAL);

        plot.test(helper -> helper.succeedWhen(() -> {
            var cablePart = helper.getPart(new BlockPos(2, 0, 0), null, OverclockedSmartCablePart.class);
            var node = cablePart.getGridNode();
            helper.check(node != null, "overclocked cable at (2,0,0) has no grid node");

            helper.check(node.getMaxChannels() == OverclockedSmartCablePart.BASE_CHANNEL_CAPACITY,
                    "expected max channels " + OverclockedSmartCablePart.BASE_CHANNEL_CAPACITY
                            + ", was " + node.getMaxChannels());
            helper.check(node.getUsedChannels() == 16,
                    "expected all 16 terminals to be routed a channel through the trunk cable, but only "
                            + node.getUsedChannels() + " channels were in use");
        }));
    }
}
