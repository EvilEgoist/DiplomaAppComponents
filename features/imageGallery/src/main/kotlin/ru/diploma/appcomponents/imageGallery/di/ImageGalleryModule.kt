package ru.diploma.appcomponents.imageGallery.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.diploma.appcomponents.imageGallery.data.repository.ImagesRepositoryImpl
import ru.diploma.appcomponents.imageGallery.data.repository.SearchSuggestionsRepositoryImpl
import ru.diploma.appcomponents.imageGallery.domain.repository.ImagesRepository
import ru.diploma.appcomponents.imageGallery.domain.repository.SearchSuggestionsRepository
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetImagesUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.GetSortOrderUseCase
import ru.diploma.appcomponents.imageGallery.domain.usecase.getImages
import ru.diploma.appcomponents.imageGallery.domain.usecase.getSortOrder
import javax.inject.Singleton

@Module(includes = [ImageGalleryModule.BindsRepoModule::class])
@InstallIn(SingletonComponent::class)
object ImageGalleryModule {

    @Provides
    fun provideGetImagesUseCase(
        imagesRepository: ImagesRepository
    ): GetImagesUseCase {
        return GetImagesUseCase {
            getImages(imagesRepository)
        }
    }

    @Provides
    fun provideGetSortOrderUseCase(
        imagesRepository: ImagesRepository
    ): GetSortOrderUseCase{
        return GetSortOrderUseCase {
            getSortOrder(imagesRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsRepoModule {

        @Binds
        @Singleton
        fun bindImagesRepository(impl: ImagesRepositoryImpl): ImagesRepository

        @Binds
        @Singleton
        fun bindSearchSuggestionsRepository(impl: SearchSuggestionsRepositoryImpl): SearchSuggestionsRepository
    }
}