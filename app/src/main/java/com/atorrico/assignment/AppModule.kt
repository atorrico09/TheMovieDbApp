package com.atorrico.assignment

import android.content.Context
import com.atorrico.assignment.data.datasource.api.MovieRemoteDataSource
import com.atorrico.assignment.data.datasource.api.service.MovieRetrofitService
import com.atorrico.assignment.data.datasource.database.MovieDao
import com.atorrico.assignment.data.datasource.database.MovieDatabase
import com.atorrico.assignment.data.repository.MovieRepositoryImpl
import com.atorrico.assignment.domain.repository.MovieRepository
import com.atorrico.assignment.presentation.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieRetrofitService =
        retrofit.create(MovieRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieRetrofitService: MovieRetrofitService) =
        MovieRemoteDataSource(movieRetrofitService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        MovieDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) =
        db.movieDao()

    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, localDataSource)
    }
}