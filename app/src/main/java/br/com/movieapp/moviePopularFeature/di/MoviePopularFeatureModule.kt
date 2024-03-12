package br.com.movieapp.moviePopularFeature.di

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.moviePopularFeature.data.repository.MoviePopularRepositoryImpl
import br.com.movieapp.moviePopularFeature.data.source.MoviePopularRemoteDataSourceImpl
import br.com.movieapp.moviePopularFeature.domain.repository.MoviePopularRepository
import br.com.movieapp.moviePopularFeature.domain.source.MoviePopularRemoteDataSource
import br.com.movieapp.moviePopularFeature.domain.usecase.GetPopularMoviesUseCase
import br.com.movieapp.moviePopularFeature.domain.usecase.GetPopularMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviePopularFeatureModule {

    @Provides
    @Singleton
    fun provideMovieDataSource(service: MovieService): MoviePopularRemoteDataSource =
        MoviePopularRemoteDataSourceImpl(service = service)

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: MoviePopularRemoteDataSource): MoviePopularRepository =
        MoviePopularRepositoryImpl(remoteDataSource = remoteDataSource)

    @Provides
    @Singleton
    fun provideGetMoviePopularUseCase(moviePopularRepository: MoviePopularRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCaseImpl(repository = moviePopularRepository)
}