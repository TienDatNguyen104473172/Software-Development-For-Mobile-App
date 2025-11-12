package com.example.mini_library.ui.reader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels // <-- IMPORT CẦN THIẾT
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.BookReaderActivity // <-- IMPORT CẦN THIẾT
import com.example.mini_library.R
import com.example.mini_library.WikiApplication
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterFragment : Fragment(R.layout.fragment_character) {

    // Lấy ViewModel chung của Activity cha (BookReaderActivity)
    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo Adapter
        val adapter = CharacterAdapter { character ->
            Toast.makeText(context, "Selected: ${character.name}", Toast.LENGTH_SHORT).show()
            // TODO: Mở màn hình chi tiết nhân vật
        }

        // Tìm RecyclerView trong layout
        val recyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Quan sát (Observe) danh sách nhân vật từ ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.collectLatest { characters ->
                // Khi có dữ liệu, cập nhật Adapter
                adapter.submitList(characters)
            }
        }
    }

    companion object {
        fun newInstance(): CharacterFragment {
            return CharacterFragment()
        }
    }
}