# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

TurtlShell is a multi-platform Minecraft mod framework (Fabric & NeoForge) written in Kotlin, built on Architectury. It provides a reusable API and toolkit — GUI system, event system, command registry, keybind registry, and config utilities — that other mods can depend on.

- **Minecraft:** 1.21.1 with Parchment mappings
- **Kotlin** with Fabric Kotlin and KotlinForForge
- **Architectury** for multi-platform abstraction
- **YACL3** for configuration UI

## Build Commands

```bash
./gradlew build                  # Build all modules
./gradlew :fabric:runClient      # Run Fabric dev client
./gradlew :neoforge:runClient    # Run NeoForge dev client
./gradlew :common:build          # Build common module only
```

Gradle requires `-Xmx8G` (already set in `gradle.properties`) for Minecraft decompilation. There are no test targets currently.

## Module Structure

```
common/    # Platform-agnostic API and implementations
fabric/    # Fabric entry point and event bridges
neoforge/  # NeoForge entry point and event bridges
gradle/build-logic/  # Custom Gradle convention plugins
```

All shared logic lives in `common`. Platform modules only contain entry points, event bridges, and loader-specific wiring.

## Architecture

### Platform Abstraction

`Platform` uses `ServiceLoader` to load a `PlatformHelper` implementation at runtime. The common module calls `Platform.helper` for things like config directory, mod detection, and environment type. Each platform module provides its own `PlatformHelper` impl registered via `META-INF/services`.

### Event System

`ClientEvents` in the common API exposes `ObservableEvent<T>` and `CancellableEvent<T>` fields (e.g. `TICK_POST`, `MESSAGE_RECEIVED`, `LEVEL_CONNECTED`). Platform modules subscribe to their own loader events and forward them to these common events. External mods subscribe via `ClientEvents.TICK_POST.subscribe { ... }`.

### GUI Framework

The GUI system centers on `AbstractModalScreen` — a three-pane modal (header, sidebar, body) with:
- **`SidebarContainer`** — navigation buttons generated per-page
- **`BodyContainer`** — content area that swaps between pages
- **`HeaderContainer`** — title bar with close button

Pages are registered via `addPage(label, icon) { /* init body */ }` DSL. Scrollable content extends `AbstractScrollableContainer`, which clips rendering to the viewport and handles mouse/wheel scroll with a 4px scrollbar.

Theming is done through `GuiTheme` (colors, padding, divider width). `GuiConstants` holds standard dimensions (380×200 modal, 90px sidebar width).

### Command & Keybind Registries

`CommandRegistry` and `KeybindRegistry` are central registries that external mods add groups to. `CommandGroupRegistrar` bridges to Brigadier on both platforms. Keybinds are grouped by category via `KeybindGroup`.

### Config Widgets

`OptionFactory` wraps YACL3 option builders with translation-key-driven helpers for common types (toggles, sliders, enums, color pickers, keybind pickers).

### Example Module

`cc.turtl.turtlshell.example` demonstrates correct API usage — `ExampleScreen` shows a 6-page modal, `ExampleKeybinds` registers a keybind to open it, and `ExampleConfigClient` shows YACL3 integration. Use this as a reference when adding new API surface.

## Versioning

Artifact version is built dynamically: `{base}+{mc_version}-{branch}-{build_number}` (configured in `gradle/build-logic`). The base version lives in `gradle.properties`.
