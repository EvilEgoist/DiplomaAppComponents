package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import ru.diploma.appcomponents.imageGallery.domain.repository.SearchSuggestionsRepository
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val searchSuggestionsRepository: SearchSuggestionsRepository
){
    suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>>{
        return imagesRepository.searchImages(query)
    }

    suspend fun getSearchSuggestions(query: String) {
        searchSuggestionsRepository.getSearchSuggestions(query)
    }

    suspend fun deleteSearchSuggestion(id: Int){
        searchSuggestionsRepository.deleteSuggestion(id)
    }

    suspend fun insertSearchQuery(query: String){
        searchSuggestionsRepository.insertSearchQuery(query)
    }

    fun getSuggestionsFlow(): MutableStateFlow<List<SearchHistoryModel>>{
        return searchSuggestionsRepository.getSuggestionsFlow()
    }
}
