package ru.diploma.appcomponents.imageGallery.data.remote

import NetworkResponse
import ru.diploma.appcomponents.imageGallery.data.models.SomeError
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.diploma.appcomponents.features.imageGallery.BuildConfig
import ru.diploma.appcomponents.imageGallery.data.models.SearchResponse
import ru.diploma.appcomponents.imageGallery.data.models.UnsplashImageResponse

interface UnsplashApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getImages(
        @Query("page")page: Int,
        @Query("per_page")perPage: Int,
        @Query("order_by")orderBy: String
    ): NetworkResponse<List<UnsplashImageResponse>, SomeError>


    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query")query: String,
        @Query("per_page")perPage: Int,
        @Query("order_by")orderBy: String
    ): NetworkResponse<SearchResponse, SomeError>
}