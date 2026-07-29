package dev.sixteenbitforge.aeoc.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Server-side config for this mod's own cable tiers, layered on top of (not replacing) AE2's own global
 * {@code ChannelMode} setting. {@link #CHANNEL_CAPACITY_MULTIPLIER} scales just our tiers' base capacities (16/64/
 * 128), and {@link #UNLIMITED_CHANNELS} makes just our tiers unlimited without having to flip AE2's own channel mode
 * to INFINITE for every cable in the world (vanilla included).
 */
public final class AeocConfig {
    private AeocConfig() {
    }

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue CHANNEL_CAPACITY_MULTIPLIER = BUILDER
            .comment(
                    "Multiplies the base channel capacity of every Overclocked/Hyperconductive cable tier",
                    "(16/64/128 by default). Does not affect vanilla AE2 cables -- use AE2's own channel mode",
                    "config for that. Ignored if unlimitedChannels is true.")
            .defineInRange("channelCapacityMultiplier", 1.0, 0.01, 1024.0);

    public static final ModConfigSpec.BooleanValue UNLIMITED_CHANNELS = BUILDER
            .comment(
                    "If true, every Overclocked/Hyperconductive cable carries unlimited channels, regardless of",
                    "AE2's own channel mode setting. Vanilla AE2 cables are unaffected -- set AE2's own channel",
                    "mode to INFINITE if you want that too.")
            .define("unlimitedChannels", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
