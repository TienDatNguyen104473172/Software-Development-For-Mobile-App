package com.example.mini_library.ui // Đảm bảo đúng package của bạn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.R
import com.example.mini_library.data.Novel

// Adapter này dùng ListAdapter để tự động xử lý thay đổi dữ liệu (rất hiệu quả)
class NovelAdapter(private val onItemClicked: (Novel) -> Unit) :
    ListAdapter<Novel, NovelAdapter.NovelViewHolder>(DiffCallback) {

    // 1. ViewHolder: Giữ các View của MỘT trang bìa sách
    class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Tìm các View bên trong file item_novel_cover.xml
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        // private val imageCover: ImageView = itemView.findViewById(R.id.imageCover) // Sẽ dùng sau

        // Hàm này gán dữ liệu từ 'Novel' vào các View
        fun bind(novel: Novel) {
            textTitle.text = novel.title
            textAuthor.text = novel.author
            // Sau này chúng ta sẽ dùng thư viện Glide/Coil để load ảnh bìa vào imageCover
        }
    }

    // 2. Được gọi khi ViewPager2 cần tạo một trang mới
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        // "Thổi phồng" (Inflate) layout mới của chúng ta
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_novel_cover, parent, false)
        return NovelViewHolder(view)
    }

    // 3. Được gọi khi ViewPager2 muốn hiển thị dữ liệu cho một trang
    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = getItem(position)
        holder.bind(novel)
        // Bắt sự kiện click vào trang sách
        holder.itemView.setOnClickListener {
            onItemClicked(novel)
        }
    }

    // 4. Đối tượng giúp so sánh danh sách cũ và mới (rất quan trọng cho ListAdapter)
    companion object DiffCallback : DiffUtil.ItemCallback<Novel>() {
        override fun areItemsTheSame(oldItem: Novel, newItem: Novel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Novel, newItem: Novel): Boolean {
            return oldItem == newItem
        }
    }
}