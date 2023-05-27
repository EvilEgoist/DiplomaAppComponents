package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.imageGallery.domain.usecase.ImageDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val imageDetailsUseCase: ImageDetailsUseCase
) : ViewModel() {

    private val _detailsScrenUiStateFlow =
        MutableStateFlow<DetailsScreenUiState>(DetailsScreenUiState())
    val detailsScreenUiStateFlow = _detailsScrenUiStateFlow
    fun getImageDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _detailsScrenUiStateFlow.value = DetailsScreenUiState(true)
                _detailsScrenUiStateFlow.value = DetailsScreenUiState(
                    isLoading = false,
                    imageDetailsUseCase.getImageDetails(id),
                    ""
                )
            } catch (e: Exception) {
                _detailsScrenUiStateFlow.value = DetailsScreenUiState(error = e.message.orEmpty())
            }
        }
    }
}
