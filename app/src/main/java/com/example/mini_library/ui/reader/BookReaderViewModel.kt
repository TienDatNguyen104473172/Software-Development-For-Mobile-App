package com.example.mini_library.ui.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mini_library.data.Arc
import com.example.mini_library.data.Character
import com.example.mini_library.data.CultivationRank
import com.example.mini_library.data.Novel
import com.example.mini_library.data.Sect
import com.example.mini_library.data.Technique
import com.example.mini_library.data.WikiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookReaderViewModel(
    private val repository: WikiRepository,
    private val novelId: Int
) : ViewModel() {

    // 1. Các luồng dữ liệu
    val novel: Flow<Novel> = repository.getNovel(novelId)
    val arcs: Flow<List<Arc>> = repository.getArcsByNovel(novelId)
    val characters: Flow<List<Character>> = repository.getCharactersByNovel(novelId)
    val sects: Flow<List<Sect>> = repository.getSectsByNovel(novelId)
    val techniques: Flow<List<Technique>> = repository.getTechniquesByNovel(novelId)
    val ranks: Flow<List<CultivationRank>> = repository.getRanksByNovel(novelId)

    // 2. CÁC HÀM INSERT (ĐÃ SỬA CHUẨN)

    // Arc: Thêm tham số imageUrl, map 'summary' -> 'description'
    fun insertArc(title: String, order: Int, summary: String, imageUrl: String) = viewModelScope.launch {
        val finalUri = if (imageUrl.isBlank()) null else imageUrl
        repository.insertArc(Arc(novelId = novelId, title = title, order = order, description = summary, imageUri = finalUri))
    }

    // Character: map 'bio' -> 'description'
    fun insertCharacter(name: String, role: String, bio: String, imageUrl: String) = viewModelScope.launch {
        val finalUri = if (imageUrl.isBlank()) null else imageUrl
        repository.insertCharacter(Character(novelId = novelId, name = name, role = role, description = bio, imageUri = finalUri))
    }

    // Sect: Ghép location vào description
    fun insertSect(name: String, description: String, location: String, imageUrl: String) = viewModelScope.launch {
        val finalUri = if (imageUrl.isBlank()) null else imageUrl
        val fullDesc = "$description (Địa điểm: $location)"
        repository.insertSect(Sect(novelId = novelId, name = name, description = fullDesc, imageUri = finalUri))
    }

    // Technique: Ghép level vào description
    fun insertTechnique(name: String, description: String, level: String, imageUrl: String) = viewModelScope.launch {
        val finalUri = if (imageUrl.isBlank()) null else imageUrl
        val fullDesc = "$description (Cấp độ: $level)"
        repository.insertTechnique(Technique(novelId = novelId, name = name, description = fullDesc, imageUri = finalUri))
    }

    // Rank: Giữ nguyên
    fun insertRank(name: String, order: Int, description: String, imageUrl: String) = viewModelScope.launch {
        val finalUri = if (imageUrl.isBlank()) null else imageUrl
        repository.insertRank(CultivationRank(novelId = novelId, name = name, order = order, description = description, imageUri = finalUri))
    }

    // 3. CÁC HÀM DELETE (Giữ nguyên)
    fun deleteArc(arc: Arc) = viewModelScope.launch { repository.deleteArc(arc) }
    fun deleteCharacter(character: Character) = viewModelScope.launch { repository.deleteCharacter(character) }
    fun deleteSect(sect: Sect) = viewModelScope.launch { repository.deleteSect(sect) }
    fun deleteTechnique(technique: Technique) = viewModelScope.launch { repository.deleteTechnique(technique) }
    fun deleteRank(rank: CultivationRank) = viewModelScope.launch { repository.deleteRank(rank) }
}

// Factory (Giữ nguyên)
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
