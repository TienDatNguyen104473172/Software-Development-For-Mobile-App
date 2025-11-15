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

class SectFragment : Fragment(R.layout.fragment_character) {

    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SectAdapter(
            onItemClicked = { sect ->
                Toast.makeText(context, "Selected: ${sect.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = { sect ->
                viewModel.deleteSect(sect)
            }
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sects.collectLatest { sects ->
                adapter.submitList(sects)
            }
        }
    }

    companion object {
        fun newInstance(): SectFragment {
            return SectFragment()
        }
    }
}