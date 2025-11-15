package com.example.mini_library.ui.reader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.BookReaderActivity
import com.example.mini_library.R
import com.example.mini_library.WikiApplication
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Tái sử dụng layout fragment_character vì nó chỉ có 1 RecyclerView
class CultivationFragment : Fragment(R.layout.fragment_character) {

    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo Adapter với 2 hành động: Click (xem) và Long Click (xóa)
        val adapter = CultivationAdapter(
            onItemClicked = { rank ->
                Toast.makeText(context, "Cấp ${rank.order}: ${rank.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = { rank ->
                // Gọi ViewModel để xóa
                viewModel.deleteRank(rank)
                Toast.makeText(context, "Đã xóa cảnh giới: ${rank.name}", Toast.LENGTH_SHORT).show()
            }
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Quan sát dữ liệu 'ranks' từ ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ranks.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }

    companion object {
        fun newInstance() = CultivationFragment()
    }
}