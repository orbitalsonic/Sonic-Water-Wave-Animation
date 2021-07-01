package com.orbitalsonic.waterwaveanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
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
            showMessage("Progress: $progress, Max: $max")
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
                    R.id.btnSquare -> waterWaveView.setShape(WaterWaveView.Shape.SQUARE)
                    R.id.btnHeart -> waterWaveView.setShape(WaterWaveView.Shape.HEART)
                    R.id.btnStar -> waterWaveView.setShape(WaterWaveView.Shape.STAR)
                }
            } else {
                if (toggleGroup.checkedButtonId == View.NO_ID) {
                    waterWaveView.setShape(WaterWaveView.Shape.CIRCLE)
                }
            }
        }

    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}