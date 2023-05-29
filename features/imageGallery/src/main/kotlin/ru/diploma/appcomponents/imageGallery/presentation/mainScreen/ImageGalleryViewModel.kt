package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetMainScreenSortOrderUseCase
import ru.diploma.appcomponents.imageGallery.util.SortOrder
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getMainScreenSortOrderUseCase: GetMainScreenSortOrderUseCase,
) : ViewModel() {

    private var _images = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val images = _images.asStateFlow()

    private val _sortOrderFlow = getMainScreenSortOrderUseCase()
    val sortOrderFlow = _sortOrderFlow.asStateFlow()

    init {
        getImages()
    }

    fun changeSortOrder(sortOrder: SortOrder) {
        _sortOrderFlow.value = sortOrder
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            getImagesUseCase().cachedIn(viewModelScope).collect {
                _images.value = it
            }
        }
    }
}