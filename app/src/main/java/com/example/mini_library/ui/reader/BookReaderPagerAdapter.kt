package com.example.mini_library.ui.reader // Đảm bảo đúng package

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// 1. Adapter bây giờ chỉ cần 'activity' (nó không cần Novel nữa)
//    Vì các Fragment con sẽ tự lấy data từ ViewModel
class BookReaderPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // 2. TÊN CÁC TAB MỚI (Đã cập nhật)
    val fragmentTitles = listOf("Overview", "Characters", "Sects", "Techniques", "Cultivation")

    // 3. Tổng số trang (5)
    override fun getItemCount(): Int {
        return fragmentTitles.size
    }

    // 4. Quyết định Fragment nào sẽ được hiển thị
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // Trang 0: OverviewFragment (Đã có, dùng bản mới)
            0 -> OverviewFragment()

            // Trang 1: CharacterFragment (Đã có, dùng bản mới)
            1 -> CharacterFragment()

            // Trang 2: Sects (Tông Môn) - Dùng placeholder
            2 -> PlaceholderFragment.newInstance("Sects (Coming Soon)")

            // Trang 3: Techniques (Công Pháp) - Dùng placeholder
            3 -> PlaceholderFragment.newInstance("Techniques (Coming Soon)")

            // Trang 4: Cultivation (Tu Luyện) - Dùng placeholder
            4 -> PlaceholderFragment.newInstance("Cultivation (Coming Soon)")

            else -> Fragment() // Trường hợp dự phòng
        }
    }
}