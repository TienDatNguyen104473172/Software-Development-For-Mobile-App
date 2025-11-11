package com.example.mini_library.data

import kotlinx.coroutines.flow.Flow

// Repository nhận vào các DAO để làm việc với Database
class WikiRepository(
    private val novelDao: NovelDao,
    private val arcDao: ArcDao,
    private val characterDao: CharacterDao
) {
    // --- Novel (Truyện) ---
    val allNovels: Flow<List<Novel>> = novelDao.getAllNovels()

    fun getNovel(id: Int): Flow<Novel> = novelDao.getNovel(id)

    suspend fun insertNovel(novel: Novel) = novelDao.insert(novel)
    suspend fun updateNovel(novel: Novel) = novelDao.update(novel)
    suspend fun deleteNovel(novel: Novel) = novelDao.delete(novel)

    // --- Arc (Hồi) ---
    fun getArcsByNovel(novelId: Int): Flow<List<Arc>> = arcDao.getArcsByNovel(novelId)
    suspend fun insertArc(arc: Arc) = arcDao.insert(arc)
    suspend fun deleteArc(arc: Arc) = arcDao.delete(arc)

    // --- Character (Nhân vật) ---
    fun getCharactersByNovel(novelId: Int): Flow<List<Character>> = characterDao.getCharactersByNovel(novelId)
    suspend fun insertCharacter(character: Character) = characterDao.insert(character)
    suspend fun deleteCharacter(character: Character) = characterDao.delete(character)
}