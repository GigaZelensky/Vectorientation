# Vectorientation

Vectorientation is a client‑side mod for Minecraft that adds squash and stretch
effects to falling blocks and certain entities. Blocks and TNT rotate to match
their motion, while mobs can optionally squish when moving vertically.

## Building

This project uses [Gradle](https://gradle.org/). To create a mod jar run:

```bash
./gradlew build
```

The output will be located in `build/libs/`.

## Installation

Copy the generated jar to your Minecraft `mods` folder along with Fabric
Loader and Fabric API for the target game version.

## Configuration

On first launch a configuration file is created in your game directory. It
contains options to enable the squash effect (`squetch`), adjust how much it
increases with speed (`warp_factor`), and define a blacklist of blocks that
should not be affected.

## Features

- Falling blocks align with their velocity and stretch as they fall.
- TNT and minecarts get similar treatment.
- Mobs can squish when moving up or down if the option is enabled.
