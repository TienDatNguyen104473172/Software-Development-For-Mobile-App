package com.example.mini_library // Đảm bảo đúng package của bạn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mini_library.data.Novel
import com.example.mini_library.ui.MainViewModel
import com.example.mini_library.ui.MainViewModelFactory
import com.example.mini_library.ui.NovelAdapter
import com.example.mini_library.BuildConfig // Import file BuildConfig

class MainActivity : AppCompatActivity() {

    // 1. Khởi tạo ViewModel
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WikiApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Tìm ViewPager2 và các nút
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerNovels)
        val btnPrev = findViewById<View>(R.id.btnPrev)
        val btnNext = findViewById<View>(R.id.btnNext)

        // 3. Khởi tạo Adapter
        val adapter = NovelAdapter { novel ->
            onNovelClicked(novel)
        }
        viewPager.adapter = adapter

        // 4. Tạo hiệu ứng "Cover Flow"
        viewPager.offscreenPageLimit = 1
        viewPager.setPageTransformer { page, position ->
            val absPos = Math.abs(position)
            page.scaleY = 0.85f + (1 - absPos) * 0.15f
            page.scaleX = 0.85f + (1 - absPos) * 0.15f
            page.alpha = 0.5f + (1 - absPos) * 0.5f
        }

        // 5. Quan sát (Observe) dữ liệu - ĐÃ SỬA LỖI (CHỈ 1 LẦN)
        viewModel.allNovels.observe(this) { novels ->

                adapter.submitList(novels)

        }

        // 6. Xử lý nút mũi tên
        btnPrev.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true)
            }
        }

        btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1, true)
            }
        }

        // 7. Xử lý nút FAB (+) - ĐÃ SỬA LỖI (ĐẶT BÊN NGOÀI OBSERVE)
        val fab = findViewById<View>(R.id.fabAddNewNovel)
        if (BuildConfig.DEBUG) {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener {
                val intent = Intent(this, AddNovelActivity::class.java)
                startActivity(intent)
            }
        } else {
            fab.visibility = View.GONE
        }
    }

    // 8. Hàm onNovelClicked
    private fun onNovelClicked(novel: Novel) {
        // ĐÃ SỬA: Bỏ toàn bộ logic if/else hardcode. Mở màn hình đọc truyện cho TẤT CẢ các cuốn sách.
        val intent = android.content.Intent(this, BookReaderActivity::class.java)
        intent.putExtra(BookReaderActivity.EXTRA_NOVEL_ID, novel.id)
        startActivity(intent)
    }

    }
