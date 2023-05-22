package ru.diploma.appcomponents.imageGallery.data.models

import androidx.room.Embedded
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AuthorResponse(
    @SerialName("links")
    @Embedded
    val authorLinks: AuthorLinksResponse,
    @SerialName("username")
    val authorName: String
)
