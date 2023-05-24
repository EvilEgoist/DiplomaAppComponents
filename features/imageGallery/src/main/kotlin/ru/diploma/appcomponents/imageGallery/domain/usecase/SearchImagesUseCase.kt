package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
){
    suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>>{
        return imagesRepository.searchImages(query)
    }

    suspend fun getSearchSuggestions(query: String): List<SearchHistoryModel> {
        return imagesRepository.getSearchSuggestions(query)
    }

    suspend fun deleteSearchSuggestion(id: Int){
        imagesRepository.deleteSuggestion(id)
    }

    suspend fun insertSearchQuery(query: String){
        imagesRepository.insertSearchQuery(query)
    }

    suspend fun getLastSearchSuggestions(): List<SearchHistoryModel> {
        return imagesRepository.getLastSearchSuggestions()
    }
}
