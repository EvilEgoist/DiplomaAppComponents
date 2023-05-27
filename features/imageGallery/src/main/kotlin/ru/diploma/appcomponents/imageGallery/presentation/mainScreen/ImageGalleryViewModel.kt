package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.imageGallery.domain.usecase.DeleteImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetSortOrderUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetNewImagesUseCase
import ru.diploma.appcomponents.imageGallery.util.SortOrder
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getSortOrderUseCase: GetSortOrderUseCase,
    private val deleteImagesUseCase: DeleteImagesUseCase,
    private val getNewImagesUseCase: GetNewImagesUseCase
) : ViewModel() {

    var getImages = getImagesUseCase()

    val detectChangesInDataFlow = MutableStateFlow(1)

    private val _sortOrderFlow = getSortOrderUseCase()
    val sortOrderFlow = _sortOrderFlow.asStateFlow()

    fun changeSortOrder(sortOrder: SortOrder){
        _sortOrderFlow.value = sortOrder
        getImages = getNewImagesUseCase.getNewImagesPagingData()
    }
}