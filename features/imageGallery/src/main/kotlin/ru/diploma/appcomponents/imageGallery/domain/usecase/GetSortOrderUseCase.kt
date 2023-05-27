package ru.diploma.appcomponents.imageGallery.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import ru.diploma.appcomponents.imageGallery.util.SortOrder
import javax.inject.Inject

fun interface GetSortOrderUseCase : () -> MutableStateFlow<SortOrder>

fun getSortOrder(
    imagesRepository: ImagesRepository
): MutableStateFlow<SortOrder> = imagesRepository
    .getSortOrderFlow()
