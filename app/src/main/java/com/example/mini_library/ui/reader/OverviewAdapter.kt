package com.example.mini_library.ui.reader // Đảm bảo đúng package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.R
import com.example.mini_library.data.Arc // Import Arc

// 1. Định nghĩa các "Loại View"
private const val ITEM_TYPE_HEADER = 0
private const val ITEM_TYPE_ARC = 1

// 2. Adapter này dùng ListAdapter (hiệu suất cao)
// Nó nhận vào một danh sách các "OverviewItem" (chúng ta sẽ tạo ngay sau đây)
class OverviewAdapter : ListAdapter<OverviewItem, RecyclerView.ViewHolder>(OverviewDiffCallback()) {

    // 3. ViewHolder cho Header
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.header_title)
        private val summaryTextView: TextView = view.findViewById(R.id.header_summary_text)

        fun bind(header: OverviewItem.Header) {
            titleTextView.text = header.title
            summaryTextView.text = header.summary
        }
    }

    // 4. ViewHolder cho Arc
    class ArcViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.arc_title)

        fun bind(arcItem: OverviewItem.ArcItem) {
            titleTextView.text = arcItem.arc.title
            // TODO: Xử lý click để xổ ra tóm tắt Arc
        }
    }

    // 5. Quyết định xem ở vị trí 'position' nên dùng layout nào
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is OverviewItem.Header -> ITEM_TYPE_HEADER
            is OverviewItem.ArcItem -> ITEM_TYPE_ARC
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // 6. Tạo ViewHolder tương ứng với "Loại View"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_overview_header, parent, false)
                HeaderViewHolder(view)
            }
            ITEM_TYPE_ARC -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_overview_arc, parent, false)
                ArcViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // 7. Gán dữ liệu vào ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is OverviewItem.Header -> (holder as HeaderViewHolder).bind(item)
            is OverviewItem.ArcItem -> (holder as ArcViewHolder).bind(item)
        }
    }
}

// 8. "Lớp niêm phong" (Sealed Class) để chứa các loại item
// Nó cho phép ListAdapter chứa nhiều loại dữ liệu (Header và Arc)
sealed class OverviewItem {
    data class Header(val title: String, val summary: String) : OverviewItem()
    data class ArcItem(val arc: Arc) : OverviewItem()

    // Gán ID duy nhất cho mỗi item
    val id: String
        get() = when (this) {
            is Header -> title
            is ArcItem -> arc.id.toString()
        }
}

// 9. DiffCallback để so sánh
class OverviewDiffCallback : DiffUtil.ItemCallback<OverviewItem>() {
    override fun areItemsTheSame(oldItem: OverviewItem, newItem: OverviewItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OverviewItem, newItem: OverviewItem): Boolean {
        return oldItem == newItem
    }
}