package com.example.mini_library.ui.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.Arc
import com.example.mini_library.databinding.ItemOverviewArcBinding

// Adapter này giờ chỉ quản lý danh sách Arc (đơn giản hơn nhiều)
class OverviewAdapter(
    private val onDeleteClicked: (Arc) -> Unit
) : ListAdapter<Arc, OverviewAdapter.ArcViewHolder>(ArcDiffCallback()) { // Giờ nó sẽ tìm thấy class này

    class ArcViewHolder(private val binding: ItemOverviewArcBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(arc: Arc, onDeleteClicked: (Arc) -> Unit) {
            binding.arcTitle.text = arc.title

            // Tải ảnh nền Arc (nếu có)
            if (!arc.imageUri.isNullOrEmpty()) {
                binding.arcBackgroundImage.load(arc.imageUri) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                }
            } else {
                binding.arcBackgroundImage.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            // Xử lý nút Xóa (Đảm bảo ID trong XML là buttonDeleteArc)
            binding.buttonDeleteArc.setOnClickListener {
                onDeleteClicked(arc)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArcViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOverviewArcBinding.inflate(inflater, parent, false)
        return ArcViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArcViewHolder, position: Int) {
        val arc = getItem(position)
        holder.bind(arc, onDeleteClicked)
    }
}

// --- ĐÃ CHUYỂN RA NGOÀI ĐỂ SỬA LỖI ---
class ArcDiffCallback : DiffUtil.ItemCallback<Arc>() {
    override fun areItemsTheSame(oldItem: Arc, newItem: Arc) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Arc, newItem: Arc) = oldItem == newItem
}