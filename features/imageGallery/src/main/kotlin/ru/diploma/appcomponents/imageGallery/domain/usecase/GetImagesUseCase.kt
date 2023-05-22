package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.diploma.appcomponents.core.utils.resultOf
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository

fun interface GetImagesUseCase : () -> Flow<PagingData<UnsplashImage>>

fun getImages(
    imagesRepository: ImagesRepository
): Flow<PagingData<UnsplashImage>> = imagesRepository
    .getImages()
//    .map {
//        resultOf { it }
//    }
//    .catch {
//        emit(Result.failure(it))
//    }