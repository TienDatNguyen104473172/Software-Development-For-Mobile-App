package com.example.mini_library.ui.reader // Đảm bảo đúng package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.R
import com.example.mini_library.data.Character // Import Character

// Adapter này dùng ListAdapter (giống NovelAdapter)
class CharacterAdapter(private val onItemClicked: (Character) -> Unit) :
    ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffCallback) {

    // ViewHolder giữ các View của MỘT hàng nhân vật
    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textCharacterName)
        val textRole: TextView = itemView.findViewById(R.id.textCharacterRole)

        fun bind(character: Character) {
            textName.text = character.name
            textRole.text = character.role
            // TODO: Load ảnh avatar vào ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        // "Thổi phồng" layout item_character.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener {
            onItemClicked(character)
        }
    }

    // DiffCallback để so sánh
    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}