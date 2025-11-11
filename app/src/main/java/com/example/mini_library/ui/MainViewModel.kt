package com.example.mini_library.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mini_library.data.Novel
import com.example.mini_library.data.WikiRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WikiRepository) : ViewModel() {

    // --- Novel (Truyện) ---
    // Chuyển Flow thành LiveData để UI dễ quan sát
    val allNovels = repository.allNovels.asLiveData()

    fun insertNovel(novel: Novel) = viewModelScope.launch {
        repository.insertNovel(novel)
    }

    fun deleteNovel(novel: Novel) = viewModelScope.launch {
        repository.deleteNovel(novel)
    }

    // (Bạn có thể thêm các hàm cho Arc và Character tương tự khi cần)
}

// Factory để tạo ViewModel với tham số là Repository
class MainViewModelFactory(private val repository: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}