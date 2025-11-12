package com.example.mini_library // Đảm bảo đúng package

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.mini_library.ui.reader.BookReaderPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BookReaderActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: BookReaderPagerAdapter

    // (Chúng ta không cần ViewModel ở đây nữa
    // vì các Fragment con tự gọi ViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_reader)

        // 1. Nhận ID của cuốn truyện (vẫn cần thiết)
        val novelId = intent.getIntExtra(EXTRA_NOVEL_ID, -1)
        if (novelId == -1) {
            Toast.makeText(this, "Error: Novel ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. Tìm các View
        viewPager = findViewById(R.id.viewPagerReader)
        tabLayout = findViewById(R.id.tabLayout)

        // 3. Khởi tạo Adapter mới (không cần chờ data)
        pagerAdapter = BookReaderPagerAdapter(this)

        // 4. Gắn Adapter vào ViewPager2
        viewPager.adapter = pagerAdapter

        // 5. Kết nối TabLayout với ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.fragmentTitles[position]
        }.attach()
    }

    companion object {
        const val EXTRA_NOVEL_ID = "EXTRA_NOVEL_ID"
    }
}