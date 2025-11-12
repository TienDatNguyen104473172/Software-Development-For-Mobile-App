package com.example.mini_library.ui.reader

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels // <-- Import 1
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_library.BookReaderActivity // <-- IMPORT BỊ THIẾU LÀ ĐÂY!
import com.example.mini_library.R
import com.example.mini_library.WikiApplication
import com.example.mini_library.data.Arc
import com.example.mini_library.data.Novel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val viewModel: BookReaderViewModel by activityViewModels {
        BookReaderViewModelFactory(
            (requireActivity().application as WikiApplication).repository,
            // Giờ dòng này sẽ hết lỗi
            requireActivity().intent.getIntExtra(BookReaderActivity.EXTRA_NOVEL_ID, -1)
        )
    }

    private lateinit var overviewAdapter: OverviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overviewAdapter = OverviewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.overviewRecyclerView)
        recyclerView.adapter = overviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.novel.combine(viewModel.arcs) { novel, arcs ->
                buildOverviewList(novel, arcs)
            }.collectLatest { combinedList ->
                overviewAdapter.submitList(combinedList)
            }
        }
    }

    private fun buildOverviewList(novel: Novel?, arcs: List<Arc>?): List<OverviewItem> {
        val items = mutableListOf<OverviewItem>()
        if (novel != null) {
            items.add(OverviewItem.Header(novel.title, novel.summary))
        }
        if (arcs != null) {
            items.addAll(arcs.map { OverviewItem.ArcItem(it) })
        }
        return items
    }

    companion object {
        fun newInstance(title: String, summary: String): OverviewFragment {
            val fragment = OverviewFragment()
            fragment.arguments = Bundle().apply {
                putString("NOVEL_TITLE", title)
                putString("NOVEL_SUMMARY", summary)
            }
            return fragment
        }
    }
}