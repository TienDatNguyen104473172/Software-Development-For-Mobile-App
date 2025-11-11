package com.example.mini_library // Đảm bảo đúng package của bạn

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

class MainActivity : AppCompatActivity() {

    // 1. Khởi tạo ViewModel (để lấy dữ liệu)
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
            // Xử lý khi nhấn vào bìa sách
            Toast.makeText(this, "Selected: ${novel.title}", Toast.LENGTH_SHORT).show()
            // Sau này sẽ mở màn hình đọc truyện tại đây
        }
        viewPager.adapter = adapter

        // 4. Tạo hiệu ứng "Cover Flow" (trang ở giữa to hơn)
        viewPager.offscreenPageLimit = 1 // Giữ 1 trang bên cạnh
        viewPager.setPageTransformer { page, position ->
            val absPos = Math.abs(position)
            page.scaleY = 0.85f + (1 - absPos) * 0.15f
            page.scaleX = 0.85f + (1 - absPos) * 0.15f
            page.alpha = 0.5f + (1 - absPos) * 0.5f
        }

        // 5. Quan sát (Observe) dữ liệu từ ViewModel
        viewModel.allNovels.observe(this) { novels ->
            if (novels.isNullOrEmpty()) {
                // Nếu DB trống, thêm dữ liệu mẫu
                addSampleData()
            } else {
                // Nếu có dữ liệu, cập nhật lên Adapter
                adapter.submitList(novels)
            }
        }

        // 6. Xử lý nút mũi tên
        btnPrev.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true) // Lướt về trước
            }
        }

        btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1, true) // Lướt về sau
            }
        }
    }

    // Hàm thêm dữ liệu mẫu
    private fun addSampleData() {
        viewModel.insertNovel(Novel(title = "Phàm Nhân Tu Tiên", author = "Vong Ngữ", coverUrl = "", summary = ""))
        viewModel.insertNovel(Novel(title = "Tiên Nghịch", author = "Nhĩ Căn", coverUrl = "", summary = ""))
        viewModel.insertNovel(Novel(title = "Cầu Ma", author = "Nhĩ Căn", coverUrl = "", summary = ""))
    }
}