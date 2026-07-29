package dev.excal1bur.aeoc.testplots;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import appeng.api.util.AEColor;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEParts;
import appeng.server.testplots.TestPlot;
import appeng.server.testplots.TestPlotClass;
import appeng.server.testworld.PlotBuilder;

import dev.excal1bur.aeoc.parts.TieredSmartCablePart;
import dev.excal1bur.aeoc.parts.TieredSmartDenseCablePart;
import dev.excal1bur.aeoc.registration.AEOCParts;
import dev.excal1bur.aeoc.tier.AeocTiers;
import dev.excal1bur.aeoc.tier.CableShape;

/**
 * Reuses AE2's own {@code @TestPlot}/GameTest infrastructure (it scans for {@code @TestPlotClass}-annotated classes
 * across ALL loaded mods, not just itself -- see {@code appeng.server.testplots.TestPlots#findAllTestPlotClasses}) to
 * exercise our cable tiers on a real, booted ME network with a controller, instead of requiring manual in-game
 * placement.
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
        var cableItem = AEOCParts.get(AeocTiers.OVERCLOCKED, CableShape.SMART, AEColor.TRANSPARENT).get();
        var expectedCapacity = AeocTiers.OVERCLOCKED.capacityFor(CableShape.SMART);

        plot.creativeEnergyCell("0 -1 0");
        plot.block("[0,1] 0 0", AEBlocks.CONTROLLER);
        plot.cable("[2,17] 0 0", cableItem)
                .part(Direction.UP, AEParts.TERMINAL);

        plot.test(helper -> helper.succeedWhen(() -> {
            var cablePart = helper.getPart(new BlockPos(2, 0, 0), null, TieredSmartCablePart.class);
            var node = cablePart.getGridNode();
            helper.check(node != null, "overclocked cable at (2,0,0) has no grid node");

            helper.check(node.getMaxChannels() == expectedCapacity,
                    "expected max channels " + expectedCapacity + ", was " + node.getMaxChannels());
            helper.check(node.getUsedChannels() == 16,
                    "expected all 16 terminals to be routed a channel through the trunk cable, but only "
                            + node.getUsedChannels() + " channels were in use");
        }));
    }

    /**
     * A single Hyperconductive Dense Cable connected to a controller and one terminal (just enough to form a real,
     * booted grid node) reports the tier's full 128-channel capacity. The 16-channel test above already proves the
     * mixin mechanism generalizes across capacities on this exact code path, so this doesn't repeat a full
     * many-devices usage proof at 128 devices -- it targets the number itself.
     */
    @TestPlot("hyperconductive_dense_cable_128_capacity")
    public static void hyperconductiveDenseCable128Capacity(PlotBuilder plot) {
        var cableItem = AEOCParts.get(AeocTiers.HYPERCONDUCTIVE, CableShape.SMART_DENSE, AEColor.TRANSPARENT).get();
        var expectedCapacity = AeocTiers.HYPERCONDUCTIVE.capacityFor(CableShape.SMART_DENSE);

        plot.creativeEnergyCell("0 -1 0");
        plot.block("[0,1] 0 0", AEBlocks.CONTROLLER);
        plot.cable("2 0 0", cableItem)
                .part(Direction.UP, AEParts.TERMINAL);

        plot.test(helper -> helper.succeedWhen(() -> {
            var cablePart = helper.getPart(new BlockPos(2, 0, 0), null, TieredSmartDenseCablePart.class);
            var node = cablePart.getGridNode();
            helper.check(node != null, "hyperconductive cable at (1,0,0) has no grid node");

            helper.check(node.getMaxChannels() == expectedCapacity,
                    "expected max channels " + expectedCapacity + ", was " + node.getMaxChannels());
        }));
    }
}
