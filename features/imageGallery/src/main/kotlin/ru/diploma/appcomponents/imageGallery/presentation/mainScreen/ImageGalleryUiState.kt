package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.paging.PagingData
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage


data class ImageGalleryUiState(
  val isLoading: Boolean = false,
  val images: PagingData<UnsplashImage> = PagingData.empty(),
  val isError: Boolean = false
) {

  sealed class PartialState {
    object Loading : PartialState()

    data class Fetched( val data: PagingData<UnsplashImage>) : PartialState()

    data class Error(val throwable: Throwable) : PartialState()
  }

}