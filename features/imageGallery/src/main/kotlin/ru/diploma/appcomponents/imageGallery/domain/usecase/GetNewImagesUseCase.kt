package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import javax.inject.Inject

class GetNewImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    fun getNewImagesPagingData(): Flow<PagingData<UnsplashImage>> {
        return imagesRepository.getImages()
    }
}