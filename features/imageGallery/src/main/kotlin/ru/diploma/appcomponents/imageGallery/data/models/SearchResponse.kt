package ru.diploma.appcomponents.imageGallery.data.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SearchResponse(
    @SerialName("results")
    val images: List<UnsplashImageResponse>
)