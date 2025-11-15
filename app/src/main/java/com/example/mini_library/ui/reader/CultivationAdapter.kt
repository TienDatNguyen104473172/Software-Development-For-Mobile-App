package com.example.mini_library.ui.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.CultivationRank
import com.example.mini_library.databinding.ItemCultivationBinding

class CultivationAdapter(
    private val onItemClicked: (CultivationRank) -> Unit,
    private val onDeleteClicked: (CultivationRank) -> Unit
) : ListAdapter<CultivationRank, CultivationAdapter.RankViewHolder>(DiffCallback) {

    class RankViewHolder(private val binding: ItemCultivationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CultivationRank, onDeleteClicked: (CultivationRank) -> Unit) {
            // Hiện thứ tự kèm tên (ví dụ: "1. Luyện Khí")
            binding.textRankName.text = "${item.order}. ${item.name}"
            binding.textRankDesc.text = item.description

            // Tải Icon (Nếu có link)
            if (!item.imageUri.isNullOrEmpty()) {
                binding.imageRankIcon.load(item.imageUri) {
                    crossfade(true)
                    // Dùng icon mặc định nếu lỗi
                    placeholder(android.R.drawable.ic_menu_gallery)
                }
            } else {
                // Nếu không có link, hiện icon mặc định
                binding.imageRankIcon.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            binding.buttonDeleteCultivation.setOnClickListener { onDeleteClicked(item) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCultivationBinding.inflate(inflater, parent, false)
        return RankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteClicked)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CultivationRank>() {
        override fun areItemsTheSame(oldItem: CultivationRank, newItem: CultivationRank) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CultivationRank, newItem: CultivationRank) = oldItem == newItem
    }
}
