package com.atorrico.assignment

import android.content.Context
import com.atorrico.assignment.data.datasource.database.MovieDatabase
import com.atorrico.assignment.data.datasource.database.MovieDao
import com.atorrico.assignment.data.datasource.api.MovieRemoteDataSource
import com.atorrico.assignment.data.datasource.api.service.MovieRetrofitService
import com.atorrico.assignment.data.repository.MovieRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieRetrofitService = retrofit.create(MovieRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieRetrofitService: MovieRetrofitService) = MovieRemoteDataSource(movieRetrofitService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = MovieDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MovieRemoteDataSource,
                          localDataSource: MovieDao
    ) =
        MovieRepositoryImpl(remoteDataSource, localDataSource)
}