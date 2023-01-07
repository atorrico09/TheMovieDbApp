package com.atorrico.assignment.data.repository

import androidx.lifecycle.LiveData
import com.atorrico.assignment.data.datasource.api.MovieRemoteDataSource
import com.atorrico.assignment.data.datasource.api.model.MovieApiModel
import com.atorrico.assignment.data.datasource.api.response.GetMovieResponse
import com.atorrico.assignment.data.datasource.database.MovieDao
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.domain.repository.MovieRepository
import com.atorrico.assignment.presentation.utils.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) : MovieRepository {

    override suspend fun getMovie(id: Int): Result<MovieApiModel> =
        remoteDataSource.getMovie(id)

    override suspend fun getMovieList(): Result<GetMovieResponse> =
        remoteDataSource.getMovieList()

    override fun getMovieListDao(): LiveData<List<MovieEntityModel>> =
        localDataSource.getMovieListDao()

    override fun getMovieDao(id: Int): LiveData<MovieEntityModel> =
        localDataSource.getMovieDao(id)

    override suspend fun insertMovie(movie: MovieEntityModel) =
        localDataSource.insert(movie)

}