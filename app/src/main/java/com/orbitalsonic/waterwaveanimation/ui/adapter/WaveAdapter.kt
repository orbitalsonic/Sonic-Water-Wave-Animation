package com.orbitalsonic.waterwaveanimation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.waterwaveanimation.R
import com.orbitalsonic.waterwaveanimation.databinding.ItemWaveBinding
import com.orbitalsonic.waterwaveanimation.ui.model.WaveItem

private val WaveItemDiff = object : DiffUtil.ItemCallback<WaveItem>() {
    override fun areItemsTheSame(oldItem: WaveItem, newItem: WaveItem): Boolean =
        oldItem.type == newItem.type

    override fun areContentsTheSame(oldItem: WaveItem, newItem: WaveItem): Boolean =
        oldItem == newItem
}

class WaveAdapter(
    private val onWaveSelected: (position: Int, item: WaveItem) -> Unit,
) : ListAdapter<WaveItem, WaveAdapter.WaveViewHolder>(WaveItemDiff) {

    private var selectedPosition: Int = 0

    fun setSelectedPosition(position: Int) {
        if (position == selectedPosition || position !in 0 until itemCount) return
        val old = selectedPosition
        selectedPosition = position
        notifyItemChanged(old, PAYLOAD_SELECTION)
        notifyItemChanged(selectedPosition, PAYLOAD_SELECTION)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaveViewHolder {
        val binding = ItemWaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaveViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    override fun onBindViewHolder(holder: WaveViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindSelection(position == selectedPosition)
        }
    }

    inner class WaveViewHolder(
        private val binding: ItemWaveBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onWaveSelected(pos, getItem(pos))
                    setSelectedPosition(pos)
                }
            }
        }

        fun bind(item: WaveItem, selected: Boolean) {
            binding.textWaveTitle.text = item.title
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
