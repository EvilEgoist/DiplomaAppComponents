package ru.diploma.appcomponents.imageGallery.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.data.local.UnsplashDatabase
import ru.diploma.appcomponents.imageGallery.data.models.localonly.SearchHistoryDbModel
import ru.diploma.appcomponents.imageGallery.data.models.mapper.toDomainModel
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.repository.SearchSuggestionsRepository
import javax.inject.Inject

class SearchSuggestionsRepositoryImpl @Inject constructor(
    private val unsplashDatabase: UnsplashDatabase,
): SearchSuggestionsRepository {

    private val suggestionsFlow = MutableStateFlow<List<SearchHistoryModel>>(emptyList())

    override suspend fun getSearchSuggestions(query: String) {
        suggestionsFlow.value = unsplashDatabase.searchHistoryDao().getSearchSuggestions(query).map { it.toDomainModel() }
    }

    override suspend fun deleteSuggestion(id: Int) {
        unsplashDatabase.searchHistoryDao().deleteSearchSuggestion(id)
    }

    override suspend fun insertSearchQuery(query: String) {
        unsplashDatabase.searchHistoryDao().deleteByQuery(query)
        unsplashDatabase.searchHistoryDao().addNewSearchQuery(SearchHistoryDbModel(query = query))
    }

    override fun getSuggestionsFlow(): MutableStateFlow<List<SearchHistoryModel>> {
        return suggestionsFlow
    }
}