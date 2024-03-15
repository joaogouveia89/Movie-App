package br.com.movieapp.movieFavoriteFeature.di

import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.movieFavoriteFeature.data.repository.MovieFavoriteRepositoryImpl
import br.com.movieapp.movieFavoriteFeature.data.source.MovieFavoriteLocalDataSourceImpl
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import br.com.movieapp.movieFavoriteFeature.domain.source.MovieFavoriteLocalDataSource
import br.com.movieapp.movieFavoriteFeature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.AddMovieFavoriteUseCaseImpl
import br.com.movieapp.movieFavoriteFeature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.DeleteMovieFavoriteUseCaseImpl
import br.com.movieapp.movieFavoriteFeature.domain.usecase.GetMoviesFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.GetMoviesFavoriteUseCaseImpl
import br.com.movieapp.movieFavoriteFeature.domain.usecase.IsMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.IsMovieFavoriteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieFavoriteModule {

    @Provides
    @Singleton
    fun provideMovieFavoriteLocalDataSource(dao: MovieDao): MovieFavoriteLocalDataSource =
        MovieFavoriteLocalDataSourceImpl(dao = dao)

    @Provides
    @Singleton
    fun provideMovieFavoriteRepository(localDataSource: MovieFavoriteLocalDataSource): MovieFavoriteRepository=
        MovieFavoriteRepositoryImpl(localDataSource = localDataSource)

    @Provides
    @Singleton
    fun provideGetMoviesFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): GetMoviesFavoriteUseCase =
        GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)

    @Provides
    @Singleton
    fun provideAddMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): AddMovieFavoriteUseCase =
        AddMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)

    @Provides
    @Singleton
    fun provideDeleteMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): DeleteMovieFavoriteUseCase =
        DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)

    @Provides
    @Singleton
    fun provideIsMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): IsMovieFavoriteUseCase =
        IsMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
}