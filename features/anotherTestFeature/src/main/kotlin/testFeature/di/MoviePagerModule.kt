package testFeature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import testFeature.screens.MovieScreenNavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MoviePagerModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindMovieScreenNavigationFactory(factory: MovieScreenNavigationFactory): NavigationFactory

}