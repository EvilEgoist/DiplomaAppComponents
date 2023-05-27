package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.usecase.SearchImagesUseCase
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchImagesUseCase: SearchImagesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages = _searchedImages.asStateFlow()

    private val _searchSuggestions = searchImagesUseCase.getSuggestionsFlow()
    val searchSuggestions = _searchSuggestions.asStateFlow()

    init{
        viewModelScope.launch {
            _searchQuery.collect{
                searchImagesUseCase.getSearchSuggestions(it)
            }
        }
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchImages(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchImagesUseCase.insertSearchQuery(query)
            searchImagesUseCase.searchImages(query).cachedIn(viewModelScope).collect {
                _searchedImages.value = it
            }
        }
        if (_searchQuery.value != query){
            _searchQuery.value = query
        }
    }

    fun getSearchSuggestions(){
        viewModelScope.launch(Dispatchers.IO) {
            searchImagesUseCase.getSearchSuggestions(query = _searchQuery.value)
        }
    }

    fun deleteSuggestion(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            searchImagesUseCase.deleteSearchSuggestion(id)
            searchImagesUseCase.getSearchSuggestions(_searchQuery.value)
        }
    }

}