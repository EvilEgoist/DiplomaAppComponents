package ru.diploma.appcomponents.imageGallery.data.paging

import NetworkResponse
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.diploma.appcomponents.imageGallery.data.models.SomeError
import ru.diploma.appcomponents.imageGallery.data.local.UnsplashDatabase
import ru.diploma.appcomponents.imageGallery.data.remote.UnsplashApi
import ru.diploma.appcomponents.imageGallery.data.models.UnsplashImageResponse
import ru.diploma.appcomponents.imageGallery.data.models.localonly.UnsplashRemoteKeysDb
import ru.diploma.appcomponents.imageGallery.util.Constants.CURR_ORDER_BY
import ru.diploma.appcomponents.imageGallery.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase,
) : RemoteMediator<Int, UnsplashImageResponse>() {

    private val unsplashImageDao = unsplashDatabase.unsplashImageDao()
    private val unsplashRemoteKeysDao = unsplashDatabase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageResponse>
    ): MediatorResult {
        try {
            val remoteKeys = mapRemoteKeys(loadType, state)

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            //TODO сделать класс для хранения варианта сортировки, возможно синглтон?
            val response = unsplashApi.getImages(
                page = currentPage,
                perPage = ITEMS_PER_PAGE,
                orderBy = CURR_ORDER_BY
            )
            return handleResponse(response, currentPage, loadType)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun handleResponse(
        response: NetworkResponse<List<UnsplashImageResponse>, SomeError>,
        currentPage: Int,
        loadType: LoadType
    ): MediatorResult {
        when (response) {
            is NetworkResponse.Success -> {
                val endOfPaginationReached1 = response.body.isEmpty()
                parseSuccessResponseInDb(
                    currentPage,
                    endOfPaginationReached1,
                    loadType,
                    response
                )
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached1)
            }
            is NetworkResponse.ApiError -> {
                return MediatorResult.Error(Throwable("ApiError ${response.body.errors.toString()}"))
            }
            is NetworkResponse.NetworkConnectionError -> {
                return MediatorResult.Error(Throwable("NetworkConnectionError ${response.error}"))
            }
            is NetworkResponse.UnknownError -> {
                return MediatorResult.Error(Throwable("UnknownError ${response.error}"))
            }
        }
    }

    private suspend fun mapRemoteKeys(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageResponse>
    ): UnsplashRemoteKeysDb? {
        return when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)
            LoadType.PREPEND -> getRemoteKeyForFirstItem(state)
            LoadType.APPEND -> getRemoteKeyForLastItem(state)
        }
    }

    private suspend fun parseSuccessResponseInDb(
        currentPage: Int,
        endOfPaginationReached: Boolean,
        loadType: LoadType,
        response: NetworkResponse.Success<List<UnsplashImageResponse>>
    ) {
        val prevPage = if (currentPage == 1) null else currentPage - 1
        val nextPage = if (endOfPaginationReached) null else currentPage + 1

        unsplashDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                unsplashImageDao.deleteAllImages()
                unsplashRemoteKeysDao.deleteAllRemoteKeys()
            }
            val keys = response.body.map { unsplashImage ->
                UnsplashRemoteKeysDb(
                    id = unsplashImage.id,
                    prevPage = prevPage,
                    nextPage = nextPage
                )
            }
            unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
            unsplashImageDao.addImages(images = response.body)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImageResponse>
    ): UnsplashRemoteKeysDb? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImageResponse>
    ): UnsplashRemoteKeysDb? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImageResponse>
    ): UnsplashRemoteKeysDb? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}