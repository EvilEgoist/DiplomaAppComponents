package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage

data class DetailsScreenUiState(
    val isLoading: Boolean = false,
    val image: UnsplashImage? = null,
    val error: String = ""
)