package ru.diploma.appcomponents.imageGallery.domain.usecase

import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import javax.inject.Inject

class ImageDetailsUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend fun getImageDetails(id: String): UnsplashImage{
        return imagesRepository.getImageDetails(id)
    }
}