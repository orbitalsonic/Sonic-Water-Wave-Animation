package com.orbitalsonic.waterwaveanimation.ui.model

import com.orbitalsonic.waterwave.wave.WaveType

data class WaveItem(
    val title: String,
    val type: WaveType,
) {
    companion object {
        fun catalog(): List<WaveItem> =
            WaveType.values().map { type ->
                WaveItem(title = type.toReadableTitle(), type = type)
            }
    }
}

private fun WaveType.toReadableTitle(): String =
    name.split("_").joinToString(" ") { segment ->
        segment.lowercase().replaceFirstChar { ch -> ch.titlecase() }
    }
