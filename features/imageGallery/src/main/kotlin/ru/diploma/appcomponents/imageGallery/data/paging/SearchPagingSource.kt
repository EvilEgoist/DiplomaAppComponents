package ru.diploma.appcomponents.imageGallery.data.paging

import NetworkResponse
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.diploma.appcomponents.imageGallery.data.models.UnsplashImageResponse
import ru.diploma.appcomponents.imageGallery.data.remote.UnsplashApi
import ru.diploma.appcomponents.imageGallery.util.Constants.CURR_ORDER_BY
import ru.diploma.appcomponents.imageGallery.util.Constants.ITEMS_PER_PAGE

class SearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashImageResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImageResponse> {
        return try {
            val currentPage = params.key ?: 1
            val response = unsplashApi.searchImages(query = query, currentPage, perPage = ITEMS_PER_PAGE, CURR_ORDER_BY)
            when (response){
                is NetworkResponse.Success -> {
                    val endOfPaginationReached = response.body.images.isEmpty()
                    if (response.body.images.isNotEmpty()) {
                        LoadResult.Page(
                            data = response.body.images,
                            prevKey = if (currentPage == 1) null else currentPage - 1,
                            nextKey = if (endOfPaginationReached) null else currentPage + 1
                        )
                    } else {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                }
                is NetworkResponse.ApiError -> {
                    LoadResult.Error(Throwable("${response.code} ${response.body}"))
                }
                is NetworkResponse.NetworkConnectionError -> {
                    LoadResult.Error(response.error)
                }
                is NetworkResponse.UnknownError -> {
                    val error = response.error ?: Throwable("Unknown error occurred")
                    LoadResult.Error(error)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImageResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}