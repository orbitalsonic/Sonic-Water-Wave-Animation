package com.orbitalsonic.waterwaveanimation.ui.model

import androidx.annotation.DrawableRes
import com.orbitalsonic.waterwave.shape.ShapeType
import com.orbitalsonic.waterwaveanimation.R

data class ShapeItem(
    val title: String,
    val type: ShapeType,
    @DrawableRes val iconRes: Int,
) {
    companion object {
        private val titles: Map<ShapeType, String> = mapOf(
            ShapeType.CIRCLE to "Circle",
            ShapeType.WATER_DROP to "Water Drop",
            ShapeType.GLASS to "Glass",
            ShapeType.HEART to "Heart",
            ShapeType.STAR to "Star",
            ShapeType.SQUARE to "Square",
            ShapeType.RECTANGLE to "Rectangle",
            ShapeType.TRIANGLE to "Triangle",
            ShapeType.DIAMOND to "Diamond",
            ShapeType.ROUNDED_RECTANGLE to "Rounded Rect",
            ShapeType.CAPSULE to "Capsule",
            ShapeType.BLOB to "Blob",
        )

        private val icons: Map<ShapeType, Int> = mapOf(
            ShapeType.CIRCLE to R.drawable.ic_baseline_circle_24,
            ShapeType.WATER_DROP to R.drawable.ic_baseline_water_drop_24,
            ShapeType.GLASS to R.drawable.ic_baseline_glass,
            ShapeType.HEART to R.drawable.ic_baseline_heart_24,
            ShapeType.STAR to R.drawable.ic_baseline_star_24,
            ShapeType.SQUARE to R.drawable.ic_baseline_square_24,
            ShapeType.RECTANGLE to R.drawable.ic_baseline_rectangle_24,
            ShapeType.TRIANGLE to R.drawable.ic_baseline_triangle_24,
            ShapeType.DIAMOND to R.drawable.ic_baseline_diamond_24,
            ShapeType.ROUNDED_RECTANGLE to R.drawable.ic_shape_rounded_rectangle,
            ShapeType.CAPSULE to R.drawable.ic_shape_capsule,
            ShapeType.BLOB to R.drawable.ic_shape_blob,
        )

        fun catalog(): List<ShapeItem> =
            ShapeType.values().map { type ->
                ShapeItem(
                    title = titles.getValue(type),
                    type = type,
                    iconRes = icons.getValue(type),
                )
            }
    }
}
