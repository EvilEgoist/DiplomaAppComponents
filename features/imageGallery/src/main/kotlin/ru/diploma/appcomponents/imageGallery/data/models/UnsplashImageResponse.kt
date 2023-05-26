package ru.diploma.appcomponents.imageGallery.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import ru.diploma.appcomponents.core.utils.Constants.UNSPLASH_IMAGE_TABLE

@kotlinx.serialization.Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImageResponse(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerialName("color")
    val color: String,
//    @SerialName("description")
//    val description: String,
    @Embedded
    val urls: UrlsResponse,
    val likes: Int,
    @Embedded
    @SerialName("user")
    val author: AuthorResponse
)
