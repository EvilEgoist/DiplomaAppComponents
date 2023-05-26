package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.diploma.appcomponents.imageGallery.domain.usecase.ImageDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val imageDetailsUseCase: ImageDetailsUseCase
): ViewModel() {

}