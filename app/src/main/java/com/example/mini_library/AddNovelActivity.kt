package com.example.mini_library

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_library.data.Novel
import com.example.mini_library.ui.MainViewModel
import com.example.mini_library.ui.MainViewModelFactory

class AddNovelActivity : AppCompatActivity() {

    // Lấy ViewModel (vì nó chứa hàm 'insertNovel')
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WikiApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_novel)

        // Tìm các View
        val titleInput = findViewById<EditText>(R.id.editNovelTitle)
        val authorInput = findViewById<EditText>(R.id.editNovelAuthor)
        val coverUrlInput = findViewById<EditText>(R.id.editNovelCoverUrl)
        val summaryInput = findViewById<EditText>(R.id.editNovelSummary)
        val saveButton = findViewById<Button>(R.id.btnSaveNovel)

        // Xử lý sự kiện nhấn nút "Save"
        saveButton.setOnClickListener {
            // Thêm .trim() để tự động xóa dấu cách thừa ở đầu và cuối
            val title = titleInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val coverUrl = coverUrlInput.text.toString()
            val summary = summaryInput.text.toString()

            if (title.isNotEmpty() && author.isNotEmpty()) {
                // 1. Tạo đối tượng Novel mới
                val newNovel = Novel(
                    title = title,
                    author = author,
                    coverUrl = coverUrl, // (Có thể trống)
                    summary = summary
                )

                // 2. Bảo ViewModel lưu vào Database
                viewModel.insertNovel(newNovel)

                // 3. Thông báo và đóng màn hình
                Toast.makeText(this, "Novel Saved!", Toast.LENGTH_SHORT).show()
                finish() // Đóng màn hình Add, quay lại Home
            } else {
                Toast.makeText(this, "Title and Author cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}