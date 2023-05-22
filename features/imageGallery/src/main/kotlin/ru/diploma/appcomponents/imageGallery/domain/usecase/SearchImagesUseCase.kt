package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    val imagesRepository: ImagesRepository
){
    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>{
        return imagesRepository.searchImages(query)
    }
}
