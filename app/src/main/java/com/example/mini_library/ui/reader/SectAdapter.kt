package com.example.mini_library.ui.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.Sect
import com.example.mini_library.databinding.ItemSectBinding

class SectAdapter(
    private val onItemClicked: (Sect) -> Unit,
    private val onDeleteClicked: (Sect) -> Unit // <-- ĐÂY LÀ DÒNG BẠN ĐANG THIẾU
) : ListAdapter<Sect, SectAdapter.SectViewHolder>(DiffCallback) {

    class SectViewHolder(private val binding: ItemSectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sect, onItemClicked: (Sect) -> Unit, onDeleteClicked: (Sect) -> Unit) {
            binding.textSectName.text = item.name
            binding.textSectDesc.text = item.description

            if (!item.imageUri.isNullOrEmpty()) {
                binding.imageSect.load(item.imageUri) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                }
            } else {
                binding.imageSect.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            binding.root.setOnClickListener { onItemClicked(item) }
            // Đảm bảo ID nút xóa trong XML là buttonDeleteSect
            binding.buttonDeleteSect.setOnClickListener { onDeleteClicked(item) }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSectBinding.inflate(inflater, parent, false)
        return SectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectViewHolder, position: Int) {
        val item = getItem(position)
        // Truyền cả 2 hành động vào
        holder.bind(item, onItemClicked, onDeleteClicked)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Sect>() {
        override fun areItemsTheSame(oldItem: Sect, newItem: Sect) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Sect, newItem: Sect) = oldItem == newItem
    }
}