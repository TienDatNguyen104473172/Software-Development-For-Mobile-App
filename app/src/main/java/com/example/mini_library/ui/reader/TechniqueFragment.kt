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

class TechniqueFragment : Fragment(R.layout.fragment_character) {

    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TechniqueAdapter(
            onItemClicked = { item ->
                Toast.makeText(context, "Selected: ${item.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = { item ->
                viewModel.deleteTechnique(item)
            }
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            // Quan sát danh sách Công Pháp
            viewModel.techniques.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }

    companion object {
        fun newInstance() = TechniqueFragment()
    }
}