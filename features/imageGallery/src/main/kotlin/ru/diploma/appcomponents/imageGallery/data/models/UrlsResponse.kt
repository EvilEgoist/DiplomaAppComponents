package ru.diploma.appcomponents.imageGallery.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrlsResponse(
    @SerialName("regular")
    val imageUrl: String
)
