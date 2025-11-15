package com.example.mini_library.ui.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mini_library.R

// Fragment giữ chỗ cho các tính năng "Coming Soon"
class PlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Tạo một TextView đơn giản bằng code
        val textView = TextView(context).apply {
            text = arguments?.getString(ARG_TEXT) ?: "Coming Soon"
            textSize = 24f
            gravity = android.view.Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return textView
    }

    companion object {
        private const val ARG_TEXT = "arg_text"
        fun newInstance(text: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
        }
    }
}