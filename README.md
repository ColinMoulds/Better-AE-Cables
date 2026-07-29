# Better AE Cables

Better AE Cables is a NeoForge add-on for Applied Energistics 2 that expands the base cable system with higher-capacity tiers for larger and more demanding AE2 networks. It adds a clear progression path without changing the core feel of AE2.

> Extend your AE2 network with stronger cable tiers, cleaner scaling, and a more flexible infrastructure setup.

## Table of Contents

- [Features](#features)
- [Tiers](#tiers)
- [Requirements](#requirements)
- [Building](#building)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [Links](#links)
- [License](#license)

## Features

- **Higher-capacity cable tiers** — adds Overclocked and Hyperconductive variants that extend AE2's normal cable scaling.
- **Full color support** — every supported cable shape is available in the full AE2 color palette.
- **Dense-tier support** — Hyperconductive cables are exposed through the dense cable line for compatibility with existing AE2 layouts.
- **Configurable balance** — tune channel capacity scaling and enable unlimited-channel behavior without changing AE2's global channel mode.
- **AE2-friendly implementation** — built using AE2's public extension points and targeted mixins rather than forking the core pathing system.

## Tiers

| Tier | Shapes | Channels | Notes |
|---|---|---|---|
| Vanilla AE2 | Glass / Covered / Smart (normal), Covered / Smart (dense) | 8 / 32 | Reference baseline |
| Overclocked | Glass, Covered, Smart, Dense Covered, Dense Smart | 16 / 64 | Crafted from the matching vanilla AE2 cable |
| Hyperconductive | Dense Covered, Dense Smart | 128 | Crafted from the matching Overclocked dense cable |

## Requirements

| Component | Version |
|---|---|
| Minecraft | 26.1.2 |
| NeoForge | 26.1.2.x |
| Applied Energistics 2 | 26.1.10-beta or newer |
| GuideME | 26.1.12-beta or newer |
| Java | 25 |

## Building

Run the following from the project root:

```bash
./gradlew build
```

Useful development commands:

```bash
./gradlew runClient
./gradlew runGameTestServer
```

## Configuration

The mod exposes a small set of configuration options for tuning its behavior:

- `channelCapacityMultiplier` — scales only this mod's cable tiers independently of AE2's global channel-mode setting.
- `unlimitedChannels` — allows this mod's cable tiers to carry unlimited channels without changing AE2's global configuration.

## Contributing

Contributions are welcome. If you want to help improve the mod, keep changes focused and include testing details where relevant.

## Links

- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/better-ae-cables)

## License

Better AE Cables is licensed under the [GNU GPLv3](LICENSE.md). Applied Energistics 2 and GuideME remain separate mods with their own licensing and are not redistributed here.
