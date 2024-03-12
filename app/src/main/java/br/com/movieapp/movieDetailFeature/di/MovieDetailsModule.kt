package br.com.movieapp.movieDetailFeature.di

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.movieDetailFeature.data.repository.MovieDetailsRepositoryImpl
import br.com.movieapp.movieDetailFeature.data.source.MovieDetailRemoteDataSourceImpl
import br.com.movieapp.movieDetailFeature.domain.repository.MovieDetailsRepository
import br.com.movieapp.movieDetailFeature.domain.source.MovieDetailRemoteDataSource
import br.com.movieapp.movieDetailFeature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movieDetailFeature.domain.usecase.GetMovieDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(service: MovieService): MovieDetailRemoteDataSource =
        MovieDetailRemoteDataSourceImpl(service = service)

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(remoteDataSource: MovieDetailRemoteDataSource): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(remoteDataSource = remoteDataSource)

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCaseImpl(repository = repository)

}