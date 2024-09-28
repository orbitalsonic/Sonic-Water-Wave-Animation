[![](https://jitpack.io/v/orbitalsonic/Sonic-Water-Wave-Animation.svg)](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation)

# Sonic Water Wave Animation

The **Sonic Water Wave Animation** library provides an engaging and visually appealing wave animation effect for Android apps. This library supports various shapes, including **Circle**, **Water Drop**, **Glass**, **Heart**, **Star**, **Square**,  **Rectangle**, **Triangle**, and **Diamond**, making it easy to enhance your app’s user interface with smooth wave animations.

## Features

- **Custom Shapes**: Choose from Circle, Water Drop, Glass, Heart, Star, Square, Rectangle, Triangle and Diamond shapes for the wave animation.
- **Wave Properties**: Customize colors, wave strength, and progress levels.
- **Text Overlay**: Display text with customizable colors and hide/show options.
- **Border and Padding**: Add borders and adjust padding for better UI control.
- **Easy Integration**: Add and configure the water wave animation with just a few lines of XML and code.

## Installation

### Step 1: Add JitPack Repository

Add the following lines to your project's root `build.gradle` file:

```
repositories {
    google()
    mavenCentral()
    maven { url "https://jitpack.io" }
}
```  
### Step 2: Add Dependencies
In your app-level **build.gradle** file, add the library dependency. Use the latest version: [![](https://jitpack.io/v/orbitalsonic/Sonic-Water-Wave-Animation.svg)](https://jitpack.io/#orbitalsonic/Sonic-Water-Wave-Animation)
```
 implementation 'com.github.orbitalsonic:Sonic-Water-Wave-Animation:x.x.x'
```

## Usage

### XML Layout Example
To add the water wave animation in XML, use the following snippet:

```
<com.orbitalsonic.waterwave.WaterWaveView
    android:id="@+id/waterWaveView"
    android:layout_width="200dp"
    android:layout_height="200dp"
    app:animatorEnable="true"
    app:textHidden="false"
    app:shapeType="circle"
    app:frontColor="#80c5fc"
    app:behindColor="#90cbf9"
    app:borderColor="#000000"
    app:borderWidthSize="4dp"
    app:textColor="#018786"
    app:max="100"
    app:progress="30"
    app:strong="100"
    app:shapePadding="10dp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"/>
```

### Kotlin/Java Example
You can configure and control the water wave view programmatically in your activity or fragment:

```
val waterWaveView: WaterWaveView = findViewById(R.id.waterWaveView)

waterWaveView.setShape(WaterWaveView.Shape.CIRCLE)
waterWaveView.setHideText(false)
waterWaveView.setTextColor(Color.parseColor("#018786"))
waterWaveView.setFrontWaveColor(Color.parseColor("#80c5fc"))
waterWaveView.setBehindWaveColor(Color.parseColor("#90cbf9"))
waterWaveView.setBorderColor(Color.parseColor("#000000"))
waterWaveView.setBorderWidth(4F)
waterWaveView.max = 100
waterWaveView.progress = 30
waterWaveView.setWaveStrong(100)
waterWaveView.setShapePadding(10F)
waterWaveView.setAnimationSpeed(10)
```
### Adding a Listener
You can listen to progress updates using the following listener:

```
waterWaveView.setListener { progress, max ->
    Toast.makeText(this, "Progress: $progress, Max: $max", Toast.LENGTH_SHORT).show()
}
```

## Available XML Attributes

Here are the customizable attributes you can use in XML:

| Attribute         | Description                                   | Type     |
|-------------------|-----------------------------------------------|----------|
| `max`             | Maximum progress value                        | Integer  |
| `progress`        | Current progress value                        | Integer  |
| `frontColor`      | Color of the front wave                       | Color    |
| `behindColor`     | Color of the behind wave                      | Color    |
| `borderColor`     | Color of the border                           | Color    |
| `textColor`       | Color of the text                            | Color    |
| `borderWidthSize` | Width of the border                           | Dimension|
| `strong`          | Wave strength                                 | Integer  |
| `animatorEnable`  | Enable/disable animation                      | Boolean  |
| `shapeType`       | Shape of the wave container                   | Enum     |
| `textHidden`      | Hide or show the text                        | Boolean  |
| `shapePadding`    | Padding around the shape                      | Dimension|


## Key Methods

Here are the methods available to interact with the Water Wave View:

| Method                        | Description                                | Return Type |
|-------------------------------|--------------------------------------------|-------------|
| `startAnimation()`            | Starts the wave animation                   | void        |
| `stopAnimation()`             | Stops the wave animation                    | void        |
| `setWaveVector(float offset)` | Sets the wave vector offset                 | void        |
| `setWaveOffset(int offset)`   | Sets the wave animation offset              | void        |
| `setShape(Shape shape)`       | Changes the shape of the wave container     | void        |
| `setHideText(boolean hidden)`  | Hides or shows the text                     | void        |
| `setStarSpikes(int count)`    | Sets the number of spikes for star shape    | void        |
| `setBorderWidth(float width)`  | Sets the width of the border                | void        |
| `setShapePadding(float padding)`| Sets padding around the shape               | void        |
| `setWaveStrong(int strong)`    | Sets the strength of the waves              | void        |


## Screenshots

![alt text](https://github.com/orbitalsonic/Sonic-Water-Wave-Animation/blob/master/Screenshots/screengif.gif?raw=true)

# LICENSE

Copyright 2021 Muhammad Yaqoob

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
