package com.example.mini_library.ui.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.Technique
import com.example.mini_library.databinding.ItemTechniqueBinding

class TechniqueAdapter(
    private val onItemClicked: (Technique) -> Unit,
    private val onDeleteClicked: (Technique) -> Unit
) : ListAdapter<Technique, TechniqueAdapter.TechniqueViewHolder>(DiffCallback) {

    // THÊM TỪ KHÓA 'inner' Ở ĐÂY
    // 'inner' giúp ViewHolder nhìn thấy được biến 'onItemClicked' của Adapter
    inner class TechniqueViewHolder(private val binding: ItemTechniqueBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Technique) {
            binding.textTechniqueName.text = item.name
            binding.textTechniqueDesc.text = item.description

            if (!item.imageUri.isNullOrEmpty()) {
                binding.imageTechnique.load(item.imageUri) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_menu_report_image)
                }
            } else {
                binding.imageTechnique.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            // Giờ thì gọi trực tiếp được rồi (nhờ từ khóa inner)
            binding.root.setOnClickListener { onItemClicked(item) }
            binding.buttonDeleteTechnique.setOnClickListener { onDeleteClicked(item) }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechniqueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTechniqueBinding.inflate(inflater, parent, false)
        return TechniqueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TechniqueViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) // Gọi hàm bind ngắn gọn hơn
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Technique>() {
        override fun areItemsTheSame(oldItem: Technique, newItem: Technique) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Technique, newItem: Technique) = oldItem == newItem
    }
}
