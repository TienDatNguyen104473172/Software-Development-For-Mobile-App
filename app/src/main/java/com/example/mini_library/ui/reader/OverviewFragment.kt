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

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo Adapter
        val adapter = OverviewAdapter { arc ->
            viewModel.deleteArc(arc)
            Toast.makeText(context, "Deleted Arc: ${arc.title}", Toast.LENGTH_SHORT).show()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.overviewRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Quan sát danh sách Arcs đơn giản
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.arcs.collectLatest { arcs ->
                adapter.submitList(arcs)
            }
        }
    }

    companion object {
        fun newInstance(title: String, summary: String): OverviewFragment {
            return OverviewFragment()
        }
    }
}
