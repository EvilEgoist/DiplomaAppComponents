package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.diploma.appcomponents.imageGallery.domain.usecase.DeleteImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetMainScreenSortOrderUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetNewImagesUseCase
import ru.diploma.appcomponents.imageGallery.util.SortOrder
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getMainScreenSortOrderUseCase: GetMainScreenSortOrderUseCase,
    private val deleteImagesUseCase: DeleteImagesUseCase,
    private val getNewImagesUseCase: GetNewImagesUseCase
) : ViewModel() {

    var getImages = getImagesUseCase()

    private val _sortOrderFlow = getMainScreenSortOrderUseCase()
    val sortOrderFlow = _sortOrderFlow.asStateFlow()

    fun changeSortOrder(sortOrder: SortOrder){
        _sortOrderFlow.value = sortOrder
        getImages = getNewImagesUseCase.getNewImagesPagingData()
    }
}