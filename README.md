# AE2 Overclocked Cables

A [NeoForge](https://neoforged.net/) addon for [Applied Energistics 2](https://github.com/AppliedEnergistics/Applied-Energistics-2) (targeting Minecraft **26.1.2**) that adds higher-tier ME cable variants with increased channel capacity, craftable up from AE2's existing cables.

## Tiers

| Tier | Shapes | Channels | Notes |
|---|---|---|---|
| Vanilla AE2 | Glass / Covered / Smart (normal), Covered/Smart (dense) | 8 / 32 | Unmodified, for reference |
| **Overclocked** | Glass, Covered, Smart, Dense Covered, Dense (Smart) | 16 / 64 | Crafted from the matching vanilla AE2 cable |
| **Hyperconductive** | Dense Covered, Dense (Smart) | 128 | Crafted from the matching **Overclocked** dense cable |

Every shape is available in all 17 AE2 colors. The Hyperconductive tier is dense-only for now (matches the "Hyperconductive *Dense* Cable" naming and its crafting chain); the data model doesn't assume that, so a normal-capacity Hyperconductive line is a natural follow-up.

## Config

- `channelCapacityMultiplier` -- scales just this mod's cable tiers (16/64/128 base), independent of AE2's own channel mode setting.
- `unlimitedChannels` -- makes just this mod's cable tiers carry unlimited channels, without having to flip AE2's global channel mode to `INFINITE` for every cable in the world.

## How it works

AE2's channel-capacity resolution (`GridNode#getMaxChannels()`) only recognizes two hardcoded tiers (8x/32x, gated by a closed `GridFlags` enum) multiplied by a single global config factor -- there's no public API to add a third tier. This mod uses three small, targeted [Mixin](https://github.com/SpongePowered/Mixin) patches (`GridNode`, `GridConnection`, and the cable's channel-indicator calculation) that check for a custom `IGridNodeService` marker -- attached via AE2's own public `IManagedGridNode#addService` extension point -- instead of duplicating or forking AE2's pathing engine. Vanilla AE2 cables (which never carry that service) are completely unaffected.

See [`docs/`](docs/) *(TBD)* for the full recon writeup, or the Javadoc on `OverclockedCableService` and the `dev.sixteenbitforge.aeoc.mixin` package.

## Requirements

- Minecraft 26.1.2
- NeoForge 26.1.2.x
- [Applied Energistics 2](https://modrinth.com/mod/ae2) 26.1.10-beta+
- [GuideME](https://modrinth.com/mod/guideme) (AE2's own hard dependency)
- **Java 25** to build from source

## Building

```
./gradlew build
```

Run a client for testing with `./gradlew runClient`, or the AE2-integrated GameTest suite (includes this mod's own network tests) with `./gradlew runGameTestServer`.

## License

[GNU GPLv3](LICENSE.md). Applied Energistics 2 and GuideME are separate mods with their own licenses; this repo does not redistribute them.
