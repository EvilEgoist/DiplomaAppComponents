package ru.diploma.appcomponents.imageGallery.data.models.localonly

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.diploma.appcomponents.core.utils.Constants.UNSPLASH_REMOTE_KEYS_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeysDb(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
