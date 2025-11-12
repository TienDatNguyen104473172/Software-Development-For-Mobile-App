package com.example.mini_library.ui.reader // Đảm bảo đúng package

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.mini_library.data.Arc
import com.example.mini_library.data.Character
import com.example.mini_library.data.Novel
import com.example.mini_library.data.WikiRepository
import kotlinx.coroutines.flow.Flow

// 1. ViewModel này nhận Repository (để lấy data) và novelId (để biết lấy truyện nào)
class BookReaderViewModel(
    private val repository: WikiRepository,
    private val novelId: Int
) : ViewModel() {

    // 2. Lấy thông tin chi tiết của 1 cuốn truyện
    val novel: Flow<Novel> = repository.getNovel(novelId)

    // 3. Lấy danh sách Arcs của cuốn truyện đó
    val arcs: Flow<List<Arc>> = repository.getArcsByNovel(novelId)

    // 4. Lấy danh sách Nhân vật của cuốn truyện đó
    val characters: Flow<List<Character>> = repository.getCharactersByNovel(novelId)

    // (Chúng ta sẽ dùng .asLiveData() ở Activity sau)
}

// 5. Factory (nhà máy) để "sản xuất" ViewModel này
//    Vì nó cần 2 tham số (repository & novelId) nên ta phải tự tạo Factory
class BookReaderViewModelFactory(
    private val repository: WikiRepository,
    private val novelId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookReaderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookReaderViewModel(repository, novelId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}