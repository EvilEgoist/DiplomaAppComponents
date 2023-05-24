package ru.diploma.appcomponents.imageGallery.data.models.localonly

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.diploma.appcomponents.imageGallery.util.Constants

@Entity(tableName = Constants.SEARCH_HISTORY_TABLE)
data class SearchHistoryDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String
)