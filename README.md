[![](https://jitpack.io/v/orbitalsonic/Sonic-Water-Wave-Animation.svg)](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation)

# Sonic Water Wave Animation

**Android custom view** library: **animated water waves** clipped to **multiple shapes**, with **several wave formulas**, full **color / border / progress** control, and a **path-based** renderer suitable for production apps.

**Repository:** [github.com/orbitalsonic/Sonic-Water-Wave-Animation](https://github.com/orbitalsonic/Sonic-Water-Wave-Animation)

---

## Preview

> Add your own GIF/screenshots to the repo and point these links at them.

![Animated preview](preview.gif)

![Shape & wave demo](preview-shapes.png)

---

## Features

- 🌊 **Animated water wave** — multi-layer waves with optional motion
- 🌐 **Multiple wave types** — sine, sharp, triangle, and composite noise-style sampling
- 🔷 **12 clip shapes** — circle through blob; **extensible** via `ShapeGenerator` + `ShapeFactory`
- 🎨 **Full UI tuning** — wave colors, border, padding, center label
- ⚡ **Smooth animation** — `WaveAnimationController` on the main thread (`ValueAnimator`, no `HandlerThread`)
- 🧩 **Modular architecture** — `shape`, `wave`, `animation`, `view` packages
- 🎯 **Lightweight drawing** — waves drawn with **paths** (no per-frame bitmap shader)

---

## Available Shapes

Each shape maps to `com.orbitalsonic.waterwave.shape.ShapeType` and to XML `app:shapeType` via a stable integer (`legacyXmlValue`, 1–12) for **backward-compatible layouts**.

| Shape | `ShapeType` | XML `shapeType` | `legacyXmlValue` |
|-------|-------------|-----------------|-------------------|
| Circle | `CIRCLE` | `circle` | 1 |
| Water Drop | `WATER_DROP` | `water_drop` | 2 |
| Glass | `GLASS` | `glass` | 3 |
| Heart | `HEART` | `heart` | 4 |
| Star | `STAR` | `star` | 5 |
| Square (rounded) | `SQUARE` | `square` | 6 |
| Rectangle | `RECTANGLE` | `rectangle` | 7 |
| Triangle | `TRIANGLE` | `triangle` | 8 |
| Diamond | `DIAMOND` | `diamond` | 9 |
| Rounded Rectangle | `ROUNDED_RECTANGLE` | `rounded_rectangle` | 10 |
| Capsule | `CAPSULE` | `capsule` | 11 |
| Blob | `BLOB` | `blob` | 12 |

**Extensibility:** add a class implementing `ShapeGenerator`, register it in `ShapeFactory`, and add a `ShapeType` + XML enum if you need a new named shape.

---

## Wave Types

The waveform inside the clip is selected with **`setWaveType(WaveType)`** (there is **no** `app:waveType` XML attribute yet—set wave type from code).

| `WaveType` | Behavior |
|------------|----------|
| **SINE** | Smooth classic sine wave |
| **SHARP** | Stronger, sharper peaks (compressed sine profile) |
| **TRIANGLE** | Angular / triangular wave (via `asin(sin)`) |
| **NOISE** | Organic, multi-frequency blend (deterministic, not RNG) |

**Enum (library):**

```java
public enum WaveType {
    SINE,
    SHARP,
    TRIANGLE,
    NOISE
}
```

**Kotlin usage:**

```kotlin
import com.orbitalsonic.waterwave.wave.WaveType

waterWaveView.setWaveType(WaveType.SINE)
```

**Extensibility:** new wave styles belong in `WaveMath` / `WaveType` and the sampling path used by `WaveRenderer`.

---

## Installation

### Option 1: Local module

```gradle
// settings.gradle
include ':app', ':sonicwaterwave'
```

```gradle
// app/build.gradle
dependencies {
    implementation project(':sonicwaterwave')
}
```

### Option 2: JitPack

1. Add [JitPack](https://jitpack.io) to your repositories.
2. In `app/build.gradle`:

```gradle
dependencies {
    implementation 'com.github.orbitalsonic:Sonic-Water-Wave-Animation:<version>'
}
```

Use a real tag or commit from [JitPack](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation).

---

## Usage

### XML

Canonical class (recommended):

```xml
<com.orbitalsonic.waterwave.view.WaterWaveView
    android:id="@+id/waterWaveView"
    android:layout_width="200dp"
    android:layout_height="200dp"
    app:max="100"
    app:progress="50"
    app:shapeType="circle"
    app:animatorEnable="true" />
```

**Compatibility:** `com.orbitalsonic.waterwave.WaterWaveView` is a thin subclass of the view above—existing layouts keep working.

### Kotlin

```kotlin
import com.orbitalsonic.waterwave.view.WaterWaveView
import com.orbitalsonic.waterwave.shape.ShapeType
import com.orbitalsonic.waterwave.wave.WaveType

waterWaveView.setMax(100)
waterWaveView.setProgress(50)
waterWaveView.setShape(ShapeType.CIRCLE)
waterWaveView.setWaveType(WaveType.SINE)
waterWaveView.startAnimation()
// …
waterWaveView.stopAnimation()
```

**Listener:**

```kotlin
waterWaveView.setListener { progress, max ->
    // onStuffing(progress, max)
}
```

Use `com.orbitalsonic.waterwave.listener.OnWaveStuffListener` (or the deprecated root `com.orbitalsonic.waterwave.OnWaveStuffListener`, which extends it).

---

## Public API (`WaterWaveView`)

### Wave

| Method | Description |
|--------|-------------|
| `setWaveType(WaveType)` | Waveform style (`SINE`, `SHARP`, `TRIANGLE`, `NOISE`) |
| `getWaveType()` | Current `WaveType` |
| `setWaveStrong(int)` | Crest / amplitude scale (same semantics as XML `strong`) |
| `setWaveOffset(int)` | Front vs back wave phase separation (1–100 style scale) |
| `setWaveVector(float)` | Maps **0…100** to phase delta \((value - 50) / 50\) |
| `setWaveAmplitude(float)` | Multiplier on top of `setWaveStrong` (≥ 0; default `1f`) |
| `getWaveAmplitude()` | Current multiplier |
| `setWaveSpeed(float)` | Direct phase delta per animation tick |
| `getWaveSpeed()` | Current phase delta |
| `setThirdWaveLayerEnabled(boolean)` | Optional middle wave layer |
| `isThirdWaveLayerEnabled()` | Whether middle layer is on |

### Shape

| Method | Description |
|--------|-------------|
| `setShape(ShapeType)` | Preferred: set clip shape |
| `setShape(WaterWaveView.Shape)` | Legacy enum overload |
| `getShapeType()` | Current `ShapeType` |
| `getLegacyShape()` | `WaterWaveView.Shape` for legacy code |
| `setShapePadding(float)` | Outer shape inset |
| `setCornerRadius(float)` | Corner radius in px (rounded rect, square, capsule, etc.) |
| `getCornerRadius()` | Current radius |
| `setStarSpikes(int)` | Star points (minimum **3**) |

### Progress

| Method | Description |
|--------|-------------|
| `setProgress(int)` | Current level (must be ≤ `max`) |
| `getProgress()` | Current level |
| `setMax(int)` | Maximum for progress and % label (must be ≥ current progress) |
| `getMax()` | Current max |

### Appearance

| Method | Description |
|--------|-------------|
| `setFrontWaveColor(int)` | Front wave color |
| `setBehindWaveColor(int)` | Rear wave color |
| `setBorderColor(int)` | Border stroke color |
| `setBorderWidth(float)` | Border stroke width |
| `setTextColor(int)` | Center percentage text color |
| `setHideText(boolean)` | Show / hide center label |

### Animation

| Method | Description |
|--------|-------------|
| `startAnimation()` | Starts phase stepping + invalidation |
| `stopAnimation()` | Stops animation |
| `setAnimationSpeed(int)` | Milliseconds between phase steps (must be ≥ 0; re-starts animation if running) |

### Listener

| Method | Description |
|--------|-------------|
| `setListener(OnWaveStuffListener)` | Progress / max callbacks |
| `getListener()` | Current listener or `null` |

### Constants (defaults)

`DEFAULT_FRONT_WAVE_COLOR`, `DEFAULT_BEHIND_WAVE_COLOR`, `DEFAULT_BORDER_COLOR`, `DEFAULT_TEXT_COLOR` — see `WaterWaveView` sources.

---

## XML attributes

Declared on `CircularWaterWaveView` (same styleable the view reads). **Note:** the rear-wave attribute is named **`behideColor`** in XML (historical spelling).

| Attribute | Description |
|-----------|-------------|
| `app:progress` | Current progress |
| `app:max` | Maximum progress |
| `app:frontColor` | Front wave color |
| `app:behideColor` | Rear (“behind”) wave color |
| `app:borderColor` | Border color |
| `app:borderWidthSize` | Border width (dimension) |
| `app:textColor` | Center label color |
| `app:strong` | Wave strength |
| `app:shapeType` | Shape enum (`circle`, `water_drop`, … `blob`) |
| `app:shapePadding` | Shape outer padding |
| `app:animatorEnable` | Start with animation enabled |
| `app:textHidden` | Hide center percentage text |

**Runtime only:** `WaveType` — use `setWaveType(WaveType)` (no XML attr yet).

---

## Sample app (`app` module)

Interactive demo:

- **Large preview** — `WaterWaveView` in a `MaterialCardView`
- **SeekBars** — water level (0–100), wave strength (0–100)
- **Switch** — start / stop animation
- **Horizontal list** — all **`WaveType`** values
- **Grid (3 columns)** — all **`ShapeType`** values with icons, selection highlight, ripple

Run the **`app`** configuration in Android Studio to try it.

---

## Architecture (overview)

| Layer | Role |
|-------|------|
| **`shape`** | `ShapeType`, `ShapeGenerator`, `ShapeRenderer` — paths + clip + border |
| **`shape.impl`** | Concrete generators (`CircleShape`, `RoundedSquareShape`, …) |
| **`shape.factory`** | `ShapeFactory.create(ShapeType)` — registry, no switch in the view |
| **`wave`** | `WaveRenderer`, `WaveMath`, `WaveType` — path-based multi-layer waves |
| **`animation`** | `WaveAnimationController` — tick / speed / start / stop |
| **`view`** | `WaterWaveView` — orchestration only |

Root **`com.orbitalsonic.waterwave.WaterWaveView`** subclasses **`view.WaterWaveView`** for backward-compatible FQCNs.

---

## Performance notes

- **Path-based** wave geometry (no full-screen bitmap shader each frame).
- Shape **paths rebuild** on layout / shape / padding / border / corner changes—not every animation tick.
- **Production-friendly** for typical dashboard / card sizes; profile if you embed many instances in scrolling lists.

---

## License

Copyright 2021 Muhammad Yaqoob

Licensed under the **Apache License, Version 2.0**.  
Full text: [https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

## Author

**Orbitalsonic**
