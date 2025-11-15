package com.example.mini_library.ui.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.Character
import com.example.mini_library.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onItemClicked: (Character) -> Unit,
    private val onDeleteClicked: (Character) -> Unit
) : ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffCallback) {

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character, onDeleteClicked: (Character) -> Unit) {
            // 1. Gán Tên và Vai trò (Đúng ID trong XML)
            binding.textCharacterName.text = item.name
            binding.textCharacterRole.text = item.role

            // (ĐÃ XÓA dòng binding.textCharacterDesc vì XML không có View này)

            // 2. Tải ảnh (Sửa imageCharacter -> imageAvatar cho đúng XML)
            if (item.imageUri != null) {
                binding.imageAvatar.load(item.imageUri) { // <-- Sửa thành imageAvatar
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                }
            } else {
                binding.imageAvatar.setImageResource(android.R.drawable.ic_menu_gallery) // <-- Sửa thành imageAvatar
            }

            // 3. Nút xóa (Đảm bảo ID trong XML là buttonDeleteCharacter)
            binding.buttonDeleteCharacter.setOnClickListener { onDeleteClicked(item) }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteClicked)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem
    }
}