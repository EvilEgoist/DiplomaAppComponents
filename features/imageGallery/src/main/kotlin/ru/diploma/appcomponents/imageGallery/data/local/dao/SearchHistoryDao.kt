package ru.diploma.appcomponents.imageGallery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.diploma.appcomponents.imageGallery.data.models.localonly.SearchHistoryDbModel

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history_table " +
            "WHERE `query` LIKE :searchQuery || '%' ORDER BY id DESC LIMIT 6")
    suspend fun getSearchSuggestions(searchQuery: String): List<SearchHistoryDbModel>

    @Query("DELETE FROM search_history_table WHERE id = :id")
    suspend fun deleteSearchSuggestion(id: Int)

    @Query("DELETE FROM search_history_table WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewSearchQuery(item: SearchHistoryDbModel)

    @Query("SELECT * FROM search_history_table ORDER BY id DESC LIMIT 6")
    suspend fun getLastSuggestions(): List<SearchHistoryDbModel>
}