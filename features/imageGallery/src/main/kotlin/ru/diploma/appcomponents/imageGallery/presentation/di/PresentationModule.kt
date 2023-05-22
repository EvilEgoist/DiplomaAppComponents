package ru.diploma.appcomponents.imageGallery.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.imageGallery.presentation.mainScreen.ImageGalleryNavigationFactory
import ru.diploma.appcomponents.imageGallery.presentation.mainScreen.ImageGalleryUiState
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.SearchScreenNavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ImageGalleryViewModelModule {

    @Provides
    fun provideInitialImageGalleryUiState(): ImageGalleryUiState = ImageGalleryUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface ImageGallerySingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindImageGalleryNavigationFactory(factory: ImageGalleryNavigationFactory): NavigationFactory

    @Singleton
    @Binds
    @IntoSet
    fun bindSearchScreenNavigationFactory(factory: SearchScreenNavigationFactory): NavigationFactory

}