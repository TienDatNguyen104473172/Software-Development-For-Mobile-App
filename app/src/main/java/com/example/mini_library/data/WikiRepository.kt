package com.example.mini_library.data // Đảm bảo đúng package

import kotlinx.coroutines.flow.Flow

// Repository bây giờ nhận thêm SectDao vào danh sách tham số
class WikiRepository(
    private val novelDao: NovelDao,
    private val arcDao: ArcDao,
    private val characterDao: CharacterDao,
    private val sectDao: SectDao, // <-- THAM SỐ MỚI THÊM VÀO
    private val techniqueDao: TechniqueDao, // <-- Thêm tham số này
    private val cultivationDao: CultivationDao // <-- Thêm tham số mới
) {
    // --- Novel (Truyện) - Giữ nguyên ---
    val allNovels: Flow<List<Novel>> = novelDao.getAllNovels()
    fun getNovel(id: Int): Flow<Novel> = novelDao.getNovel(id)
    suspend fun insertNovel(novel: Novel) = novelDao.insert(novel)
    suspend fun updateNovel(novel: Novel) = novelDao.update(novel)
    suspend fun deleteNovel(novel: Novel) = novelDao.delete(novel)

    // --- Arc (Hồi) - Giữ nguyên ---
    fun getArcsByNovel(novelId: Int): Flow<List<Arc>> = arcDao.getArcsByNovel(novelId)
    suspend fun insertArc(arc: Arc) = arcDao.insert(arc)
    suspend fun deleteArc(arc: Arc) = arcDao.delete(arc)

    // --- Character (Nhân vật) - Giữ nguyên ---
    fun getCharactersByNovel(novelId: Int): Flow<List<Character>> = characterDao.getCharactersByNovel(novelId)
    suspend fun insertCharacter(character: Character) = characterDao.insert(character)
    suspend fun deleteCharacter(character: Character) = characterDao.delete(character)

    // --- Sect (Tông Môn) - MỚI THÊM VÀO ---
    // Các hàm này giúp ViewModel lấy dữ liệu Tông môn
    fun getSectsByNovel(novelId: Int): Flow<List<Sect>> = sectDao.getSectsByNovel(novelId)
    suspend fun insertSect(sect: Sect) = sectDao.insert(sect)
    suspend fun deleteSect(sect: Sect) = sectDao.delete(sect)

    fun getTechniquesByNovel(novelId: Int): Flow<List<Technique>> = techniqueDao.getTechniquesByNovel(novelId)
    suspend fun insertTechnique(technique: Technique) = techniqueDao.insert(technique)
    suspend fun deleteTechnique(technique: Technique) = techniqueDao.delete(technique)

    fun getRanksByNovel(novelId: Int): Flow<List<CultivationRank>> = cultivationDao.getRanksByNovel(novelId)
    suspend fun insertRank(rank: CultivationRank) = cultivationDao.insert(rank)
    suspend fun deleteRank(rank: CultivationRank) = cultivationDao.delete(rank)
}