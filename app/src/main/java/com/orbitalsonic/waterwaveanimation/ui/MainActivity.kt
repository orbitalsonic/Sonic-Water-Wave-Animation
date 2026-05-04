package com.orbitalsonic.waterwaveanimation.ui

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.waterwave.shape.ShapeType
import com.orbitalsonic.waterwave.wave.WaveType
import com.orbitalsonic.waterwaveanimation.R
import com.orbitalsonic.waterwaveanimation.databinding.ActivityMainBinding
import com.orbitalsonic.waterwaveanimation.ui.adapter.ShapeAdapter
import com.orbitalsonic.waterwaveanimation.ui.adapter.WaveAdapter
import com.orbitalsonic.waterwaveanimation.ui.model.ShapeItem
import com.orbitalsonic.waterwaveanimation.ui.model.WaveItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val shapeAdapter: ShapeAdapter by lazy {
        ShapeAdapter { _, item ->
            applyShapeTransition(item.type)
        }
    }

    private val waveAdapter: WaveAdapter by lazy {
        WaveAdapter { _, item ->
            applyWaveTypeTransition(item.type)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWavePreview()
        setupSeekBars()
        setupAnimationSwitch()
        setupWaveTypeSelector()
        setupShapeGrid()
    }

    private fun setupWavePreview() {
        binding.waterWaveView.setMax(100)
        binding.waterWaveView.setShape(ShapeType.CIRCLE)
        binding.waterWaveView.setWaveType(WaveType.SINE)
        syncWaterLevelLabel()
    }

    private fun setupSeekBars() {
        binding.seekWaterProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.waterWaveView.setProgress(progress)
                }
                syncWaterLevelLabel()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        binding.seekWaveStrength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.waterWaveView.setWaveStrong(progress)
                binding.textWaveStrengthValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        binding.textWaveStrengthValue.text = binding.seekWaveStrength.progress.toString()
    }

    private fun syncWaterLevelLabel() {
        val p = binding.seekWaterProgress.progress
        binding.textWaterLevelValue.text = getString(R.string.water_level_percent, p)
    }

    private fun setupAnimationSwitch() {
        binding.switchWaveAnimation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.waterWaveView.startAnimation()
            } else {
                binding.waterWaveView.stopAnimation()
            }
        }
    }

    private fun setupWaveTypeSelector() {
        binding.recyclerWaveTypes.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerWaveTypes.adapter = waveAdapter
        binding.recyclerWaveTypes.setHasFixedSize(true)
        waveAdapter.submitList(WaveItem.catalog()) {
            waveAdapter.setSelectedPosition(0)
        }
    }

    private fun setupShapeGrid() {
        binding.recyclerShapes.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.recyclerShapes.adapter = shapeAdapter
        binding.recyclerShapes.setHasFixedSize(true)
        shapeAdapter.submitList(ShapeItem.catalog()) {
            shapeAdapter.setSelectedPosition(0)
        }
    }

    private fun applyShapeTransition(type: ShapeType) {
        binding.waterWaveView.animate()
            .alpha(0.82f)
            .setDuration(TRANSITION_MS)
            .withEndAction {
                binding.waterWaveView.setShape(type)
                binding.waterWaveView.animate()
                    .alpha(1f)
                    .setDuration(RESTORE_MS)
                    .start()
            }
            .start()
    }

    private fun applyWaveTypeTransition(type: WaveType) {
        binding.waterWaveView.animate()
            .alpha(0.88f)
            .setDuration(TRANSITION_MS)
            .withEndAction {
                binding.waterWaveView.setWaveType(type)
                binding.waterWaveView.animate()
                    .alpha(1f)
                    .setDuration(RESTORE_MS)
                    .start()
            }
            .start()
    }

    private companion object {
        const val SPAN_COUNT = 3
        const val TRANSITION_MS = 55L
        const val RESTORE_MS = 140L
    }
}
