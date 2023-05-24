package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.usecase.SearchImagesUseCase
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchImagesUseCase: SearchImagesUseCase,
    s
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages = _searchedImages

    private val _searchSuggestions = MutableStateFlow<List<SearchHistoryModel>>(emptyList())
    val searchSuggestions = _searchSuggestions

    init{
        viewModelScope.launch {
            getInitialSuggestions()
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
            searchQuery.value = query
        }
    }

    private suspend fun getInitialSuggestions(){
        _searchSuggestions.value = searchImagesUseCase.getLastSearchSuggestions()
    }

    fun getSearchSuggestions(){
        viewModelScope.launch(Dispatchers.IO) {
            _searchSuggestions.value = searchImagesUseCase.getSearchSuggestions(searchQuery.value)
        }
    }

    fun deleteSuggestion(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            searchImagesUseCase.deleteSearchSuggestion(id)
        }
    }

}