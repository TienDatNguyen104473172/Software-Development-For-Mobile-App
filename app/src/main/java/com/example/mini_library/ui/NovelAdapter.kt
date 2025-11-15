package com.example.mini_library.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_library.R
import com.example.mini_library.data.Novel

class NovelAdapter(private val onItemClicked: (Novel) -> Unit) :
    ListAdapter<Novel, NovelAdapter.NovelViewHolder>(DiffCallback) {

    class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        private val imageCover: ImageView = itemView.findViewById(R.id.imageCover)

        fun bind(novel: Novel) {
            textTitle.text = novel.title
            textAuthor.text = novel.author

            // Smart image loading logic
            val imageToShow = novel.coverImageUri?.toUri() // Prefer local image
                ?: novel.coverUrl.takeIf { it.isNotBlank() } // Fallback to remote URL

            imageCover.load(imageToShow) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background) // Placeholder while loading
                error(R.drawable.ic_launcher_foreground) // Image to show on error
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_novel_cover, parent, false)
        return NovelViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = getItem(position)
        holder.bind(novel)
        holder.itemView.setOnClickListener {
            onItemClicked(novel)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Novel>() {
        override fun areItemsTheSame(oldItem: Novel, newItem: Novel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Novel, newItem: Novel): Boolean {
            return oldItem == newItem
        }
    }
}