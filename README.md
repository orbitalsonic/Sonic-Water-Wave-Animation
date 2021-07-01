[![](https://jitpack.io/v/orbitalsonic/Water-Wave-Animation-Android.svg)](https://jitpack.io/#orbitalsonic/Water-Wave-Animation-Android)
# Water-Wave-Animation-Android

Water Wave Animation a wave-like progressbar in Android which has four shapes, circle, square, heart and star.

# Add Gradle Files

###### Add following lines in project gradle file

```
repositories {
    google()
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

```

###### Add following dependency in app gradle file

```
 implementation 'com.github.orbitalsonic:Water-Wave-Animation-Android:1.0.0'
 
 ```
 
 # XML
 
 ```
<com.orbitalsonic.waterwave.WaterWaveView
        android:id="@+id/waterWaveView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:animatorEnable="true"
        app:textHidden="false"
        app:shapeType="circle"
        app:frontColor="#80c5fc"
        app:behideColor="#90cbf9"
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
 
 # Java or Kotlin
 
  ```
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
   
 # Attributes
   
```
max                     "Integer"
progress                "Integer"	
frontColor              "color"	
behideColor             "color"
borderColor             "color"
textColor               "color"
borderWidthSize         "Dimension"
strong                  "Integer"
animatorEnable          "boolean"
shapeType               "enum"
textHidden              "boolean"
shapePadding            "Dimension"

```
 
 # Methods
 
 ```
startAnimation()                    void
stopAnimation()                     void
setWaveVector(float offset)         void
setWaveOffset(int offset)           void
setShape(Shape shape)               void
setHideText(boolean hidden)         void
setStarSpikes(int count)            void
setBorderWidth(float width)         void
setShapePadding(float padding)      void
setWaveStrong(int strong)           void
```


 
