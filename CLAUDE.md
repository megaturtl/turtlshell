# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

TurtlShell is a Minecraft mod library/framework written in **Kotlin** for **Minecraft 1.21.1**, targeting both **Fabric** and **NeoForge** loaders via Architectury. It provides reusable utilities for GUI systems, command registration, configuration, keybindings, and client events.

## Build Commands

```bash
./gradlew build                  # Build all modules
./gradlew fabric:build           # Fabric variant only
./gradlew neoforge:build         # NeoForge variant only
./gradlew test                   # Run unit tests
./gradlew fabric:runClient       # Launch Minecraft client (Fabric)
./gradlew neoforge:runClient     # Launch Minecraft client (NeoForge)
./gradlew genSources             # Generate decompiled sources
./gradlew publish                # Publish to maven local
```

## Module Structure

Three-module Gradle project:

- **`common/`** — Shared Kotlin code: public API + internal implementations. No loader-specific imports.
- **`fabric/`** — Fabric entry points, event bridges, and platform helper implementation.
- **`neoforge/`** — NeoForge entry points, event subscribers, and platform helper implementation.

All mod metadata (mod ID, version, MC version, dependency versions) lives in **`gradle.properties`**.

## Architecture

### Platform Abstraction

`Platform.kt` uses `ServiceLoader` to load a `PlatformHelper` interface. Each loader module provides its own implementation (`PlatformHelperFabric`, `PlatformHelperNeoForge`). Common code calls `Platform.helper` for anything platform-specific (config directories, mod detection, environment type).

### Event System

`ClientEvents.kt` defines typed observable events (e.g., `TICK_POST`, `MESSAGE_RECEIVED`). The `impl/EventTypes.kt` implements `ObservableEvent<T>` and `CancellableEvent<T>`. Each loader's `EventBridge*Client` class listens to native loader events and fires the common API events.

### Command System

Commands are registered through `CommandRegistry` (API). `CommandGroupRegistrar` (impl) converts these to Brigadier command trees at runtime. Platform entry points call the registrar on the appropriate loader event.

### GUI System

Based on `AbstractModalScreen`, which has a fixed 3-part layout:
- **`HeaderContainer`** — Title bar with close button
- **`SidebarContainer`** — Navigation buttons (left panel)
- **`BodyContainer`** — Main content area with auto layout (inline/block flow, wrapping, justification)

Elements (`TextElement`, `ButtonElement`, `ToggleElement`, `TextEntryElement`, `ImageElement`) are added to the body and laid out automatically. `GuiTheme` controls colors/padding; `GuiConstants` holds default dimensions (380×200 modal, 90px sidebar).

### Keybind System

`KeybindRegistry` registers groups of keybinds. Fabric calls `ClientOptions.registerKeyBindings`; NeoForge uses `RegisterKeyMappingsEvent`. YACL (`OptionFactory`, custom widgets) is used for the config screen.

## Code Conventions

- All source lives under `cc.turtl.turtlshell`
- `api/` packages are public-facing; `impl/` packages are internal
- `example/` in common demonstrates framework usage (do not ship example code in release builds)
- The `HEADER` file contains the license header — apply it to new source files
- Dependency versions are centralized in `gradle/libs.versions.toml`
- Build conventions (Kotlin setup, Loom config, publishing) are in `gradle/build-logic/`