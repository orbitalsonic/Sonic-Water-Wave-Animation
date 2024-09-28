package com.orbitalsonic.waterwaveanimation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.orbitalsonic.waterwave.WaterWaveView

class MainActivity : AppCompatActivity() {

    private lateinit var waterWaveView: WaterWaveView
    private lateinit var addWaterLevel: ImageButton
    private lateinit var removeWaterLevel: ImageButton
    private lateinit var toggleButtonGroupIcon: MaterialButtonToggleGroup
    private var waterProgress = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        onClickListenerMethod()
    }

    private fun initViews() {
        toggleButtonGroupIcon = findViewById(R.id.toggleButtonGroupIcon)
        waterWaveView = findViewById(R.id.waterWaveView)
        addWaterLevel = findViewById(R.id.addWaterLevel)
        removeWaterLevel = findViewById(R.id.removeWaterLevel)

        waterWaveView.progress = waterProgress
    }

    private fun onClickListenerMethod() {
        waterWaveView.setListener { progress, max ->
            printLog("Progress: $progress, Max: $max")
        }

        addWaterLevel.setOnClickListener {

            if (waterProgress < 100) {
                waterProgress += 5
            }

            waterWaveView.progress = waterProgress

        }

        removeWaterLevel.setOnClickListener {

            if (waterProgress > 0) {
                waterProgress -= 5
            }

            waterWaveView.progress = waterProgress
        }

        toggleButtonGroupIcon.addOnButtonCheckedListener { toggleGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnCircle -> waterWaveView.setShape(WaterWaveView.Shape.CIRCLE)
                    R.id.btnWaterDrop -> waterWaveView.setShape(WaterWaveView.Shape.WATER_DROP)
                    R.id.btnGlass -> waterWaveView.setShape(WaterWaveView.Shape.GLASS)
                    R.id.btnHeart -> waterWaveView.setShape(WaterWaveView.Shape.HEART)
                    R.id.btnStar -> waterWaveView.setShape(WaterWaveView.Shape.STAR)
                    R.id.btnSquare -> waterWaveView.setShape(WaterWaveView.Shape.SQUARE)
                    R.id.btnRectangle -> waterWaveView.setShape(WaterWaveView.Shape.RECTANGLE)
                    R.id.btnTriangle -> waterWaveView.setShape(WaterWaveView.Shape.TRIANGLE)
                    R.id.btnDiamond -> waterWaveView.setShape(WaterWaveView.Shape.DIAMOND)
                }
            } else {
                if (toggleGroup.checkedButtonId == View.NO_ID) {
                    waterWaveView.setShape(WaterWaveView.Shape.CIRCLE)
                }
            }
        }

    }

    private fun printLog(message: String) {
        Log.d("SonicWaterWaveTAG", message)
    }

}