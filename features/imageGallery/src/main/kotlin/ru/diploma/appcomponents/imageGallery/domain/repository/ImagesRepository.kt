package ru.diploma.appcomponents.imageGallery.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage

interface ImagesRepository {

    fun getImages(): Flow<PagingData<UnsplashImage>>

    suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>>

    suspend fun getImageDetails(id: String): UnsplashImage
}