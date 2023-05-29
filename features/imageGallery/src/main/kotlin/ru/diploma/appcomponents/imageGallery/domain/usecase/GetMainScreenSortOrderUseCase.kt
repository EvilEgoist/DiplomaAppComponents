package ru.diploma.appcomponents.imageGallery.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import ru.diploma.appcomponents.imageGallery.util.SortOrder

fun interface GetMainScreenSortOrderUseCase : () -> MutableStateFlow<SortOrder>

fun getSortOrder(
    imagesRepository: ImagesRepository
): MutableStateFlow<SortOrder> = imagesRepository
    .getMainScreenSortOrderFlow()
