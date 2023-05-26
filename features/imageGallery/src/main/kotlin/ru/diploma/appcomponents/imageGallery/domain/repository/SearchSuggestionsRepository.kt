package ru.diploma.appcomponents.imageGallery.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel

interface SearchSuggestionsRepository{

    suspend fun getSearchSuggestions(query: String)

    suspend fun deleteSuggestion(id: Int)

    suspend fun insertSearchQuery(query: String)

    fun getSuggestionsFlow(): MutableStateFlow<List<SearchHistoryModel>>
}