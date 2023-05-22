package ru.diploma.appcomponents.imageGallery.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage

interface ImagesRepository {

    fun getImages(): Flow<PagingData<UnsplashImage>>

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>
}