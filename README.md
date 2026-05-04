[![](https://jitpack.io/v/orbitalsonic/Sonic-Water-Wave-Animation.svg)](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation)

# Sonic Water Wave Animation

**Android custom view** with an **animated water wave** inside **multiple clip shapes**. Tune colors, wave strength, motion, and shape—drop it into layouts or control it from Kotlin/Java.

---

## Preview

> Add your own assets to the repo root (or `docs/`) and update the paths below.

![Animated preview](preview.gif)

![Shape grid preview](preview-shapes.png)

---

## Features

- 🌊 **Animated water wave** — layered sine-style waves with optional motion
- 🔷 **Multiple shapes** — circle, heart, star, glass, and more
- 🎨 **Customizable colors** — front/back waves, border, and label
- ⚡ **Smooth animation** — main-thread controller (no `HandlerThread`)
- 🧩 **Easy integration** — XML attributes + simple API
- 🎯 **Lightweight & performant** — path-based drawing (no per-frame bitmap shader)

---

## Available Shapes

| Shape | XML `shapeType` |
|-------|-----------------|
| Circle | `circle` |
| Water Drop | `water_drop` |
| Glass | `glass` |
| Heart | `heart` |
| Star | `star` |
| Square (rounded) | `square` |
| Rectangle | `rectangle` |
| Triangle | `triangle` |
| Diamond | `diamond` |
| Rounded Rectangle | `rounded_rectangle` |
| Capsule | `capsule` |
| Blob | `blob` |

In code, use `com.orbitalsonic.waterwave.shape.ShapeType` (e.g. `ShapeType.HEART`) with `setShape(ShapeType)`.

---

## Installation

### Option 1: Local module

In your app `settings.gradle.kts` / `settings.gradle`:

```gradle
include ':app', ':sonicwaterwave'
```

In `app/build.gradle`:

```gradle
dependencies {
    implementation project(':sonicwaterwave')
}
```

### Option 2: JitPack

1. Add JitPack to your **root** `settings.gradle` (dependency resolution) or root `build.gradle` repositories.
2. In **app** `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.orbitalsonic:Sonic-Water-Wave-Animation:<version>'
}
```

Replace `<version>` with a [JitPack tag or commit](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation).

---

## Usage

### Basic XML

Recommended fully qualified class name:

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

`com.orbitalsonic.waterwave.WaterWaveView` is still supported as a thin compatibility subclass.

### Basic Kotlin

```kotlin
import com.orbitalsonic.waterwave.view.WaterWaveView
import com.orbitalsonic.waterwave.shape.ShapeType

waterWaveView.setMax(100)
waterWaveView.setProgress(50)
waterWaveView.setShape(ShapeType.CIRCLE)
waterWaveView.startAnimation()
```

```kotlin
waterWaveView.setListener { progress, max ->
    // react to progress changes
}
```

---

## Customization

Common APIs:

| API | Purpose |
|-----|---------|
| `setShape(ShapeType)` / `setShape(WaterWaveView.Shape)` | Clip shape |
| `setProgress(int)` | Current fill level |
| `setMax(int)` | Maximum for progress & label |
| `setFrontWaveColor(int)` | Front wave color |
| `setBehindWaveColor(int)` | Rear wave color |
| `setBorderColor(int)` | Outline color |
| `setBorderWidth(float)` | Outline stroke width |
| `setWaveStrong(int)` | Wave crest strength (0–100 style scale) |
| `setWaveSpeed(float)` | Phase delta per animation tick |
| `setWaveOffset(int)` | Front/back wave phase separation |
| `startAnimation()` / `stopAnimation()` | Run or stop wave motion |

Also useful: `setWaveType(WaveType)`, `setWaveAmplitude(float)`, `setCornerRadius(float)`, `setShapePadding(float)`, `setAnimationSpeed(int)`, `setThirdWaveLayerEnabled(boolean)`, `setHideText(boolean)`, `setStarSpikes(int)`.

---

## XML Attributes

| Attribute | Description |
|-----------|-------------|
| `app:progress` | Current progress value |
| `app:max` | Maximum progress value |
| `app:frontColor` | Front wave color |
| `app:behideColor` | Rear (“behind”) wave color |
| `app:borderColor` | Border color |
| `app:borderWidthSize` | Border stroke width |
| `app:strong` | Wave strength |
| `app:shapeType` | Shape enum (see [Available Shapes](#available-shapes)) |
| `app:animatorEnable` | Start with animation enabled |
| `app:textColor` | Percent label color |
| `app:textHidden` | Hide the center label |
| `app:shapePadding` | Extra inset around the shape |

---

## Sample Demo

The **`app`** module is an interactive showcase:

- Large **preview** `WaterWaveView`
- **SeekBars** for water level (0–100) and wave strength
- **Switch** to start/stop animation
- **RecyclerView** grid (3 columns) for **all shapes** with selection highlight and tap-to-apply

Open the project in Android Studio and run the **`app`** configuration to try it.

---

## Architecture

- **Shape system** — `ShapeType` + `ShapeGenerator` implementations in `shape.impl`, resolved via `ShapeFactory`. `ShapeRenderer` builds border/content paths and clips the canvas for the liquid region.
- **Wave engine** — `WaveRenderer` draws stacked wave **paths** inside the clip. `WaveMath` / `WaveType` define waveform sampling (sine, triangle, sharp, noise).
- **Animation** — `WaveAnimationController` steps phase on the main thread with `ValueAnimator`.

---

## Performance Notes

- Wave fill uses **vector paths** (no full-size bitmap shader per frame).
- Shape paths are **rebuilt** when size, padding, border, or shape type changes—not every frame.
- Suitable for **lists and dashboards** when used at reasonable sizes; prefer `hardwareLayer` only if you profile a specific clipping need on older APIs.

---

## License

Copyright 2021 Muhammad Yaqoob

Licensed under the **Apache License, Version 2.0** (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at:

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an **“AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND**, either express or implied. See the License for the specific language governing permissions and limitations under the License.

---

## Author

**Orbitalsonic**
