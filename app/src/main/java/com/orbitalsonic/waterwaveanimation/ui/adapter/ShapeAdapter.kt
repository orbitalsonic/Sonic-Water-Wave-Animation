package com.orbitalsonic.waterwaveanimation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.waterwaveanimation.R
import com.orbitalsonic.waterwaveanimation.databinding.ItemShapeBinding
import com.orbitalsonic.waterwaveanimation.ui.model.ShapeItem

private val ShapeItemDiff = object : DiffUtil.ItemCallback<ShapeItem>() {
    override fun areItemsTheSame(oldItem: ShapeItem, newItem: ShapeItem): Boolean =
        oldItem.type == newItem.type

    override fun areContentsTheSame(oldItem: ShapeItem, newItem: ShapeItem): Boolean =
        oldItem == newItem
}

class ShapeAdapter(
    private val onShapeSelected: (position: Int, item: ShapeItem) -> Unit,
) : ListAdapter<ShapeItem, ShapeAdapter.ShapeViewHolder>(ShapeItemDiff) {

    private var selectedPosition: Int = 0

    fun setSelectedPosition(position: Int) {
        if (position == selectedPosition || position !in 0 until itemCount) return
        val old = selectedPosition
        selectedPosition = position
        notifyItemChanged(old, PAYLOAD_SELECTION)
        notifyItemChanged(selectedPosition, PAYLOAD_SELECTION)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShapeViewHolder {
        val binding = ItemShapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShapeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShapeViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    override fun onBindViewHolder(holder: ShapeViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindSelection(position == selectedPosition)
        }
    }

    inner class ShapeViewHolder(
        private val binding: ItemShapeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onShapeSelected(pos, getItem(pos))
                    setSelectedPosition(pos)
                }
            }
        }

        fun bind(item: ShapeItem, selected: Boolean) {
            binding.textShapeName.text = item.title
            binding.imageShapeIcon.setImageResource(item.iconRes)
            bindSelection(selected)
        }

        fun bindSelection(selected: Boolean) {
            val density = binding.root.resources.displayMetrics.density
            val strokePx = if (selected) (3 * density).toInt() else 0
            binding.root.strokeWidth = strokePx
            val bg = ContextCompat.getColor(
                binding.root.context,
                if (selected) R.color.white else R.color.shape_item_background,
            )
            binding.root.setCardBackgroundColor(bg)
        }
    }

    private companion object {
        const val PAYLOAD_SELECTION = "selection"
    }
}
