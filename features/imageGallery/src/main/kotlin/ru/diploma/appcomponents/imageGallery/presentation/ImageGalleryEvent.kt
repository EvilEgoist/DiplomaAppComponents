package ru.diploma.appcomponents.imageGallery.presentation

sealed class ImageGalleryEvent {

    data class SearchImages(val searchQuery: String = ""): ImageGalleryEvent()
}