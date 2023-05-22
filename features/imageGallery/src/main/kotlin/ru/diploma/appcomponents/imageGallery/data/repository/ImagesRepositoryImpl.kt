package ru.diploma.appcomponents.imageGallery.data.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.diploma.appcomponents.imageGallery.data.local.UnsplashDatabase
import ru.diploma.appcomponents.imageGallery.data.models.mapper.toDomainModel
import ru.diploma.appcomponents.imageGallery.data.paging.SearchPagingSource
import ru.diploma.appcomponents.imageGallery.data.paging.UnsplashRemoteMediator
import ru.diploma.appcomponents.imageGallery.data.remote.UnsplashApi
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import ru.diploma.appcomponents.imageGallery.util.Constants
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
): ImagesRepository{

    @OptIn(ExperimentalPagingApi::class)
    override fun getImages(): Flow<PagingData<UnsplashImage>>{
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages()}
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainModel()
            }
        }
    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainModel()
            }
        }
    }
}