package com.atorrico.assignment.data.repository

import androidx.lifecycle.LiveData
import com.atorrico.assignment.data.datasource.api.MovieRemoteDataSource
import com.atorrico.assignment.data.datasource.database.MovieDao
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.data.datasource.util.ApiResult
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.domain.repository.MovieRepository
import com.atorrico.assignment.presentation.util.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) : MovieRepository {

    override suspend fun getMovie(id: Int): Result<Movie> =
        when (val apiResult = remoteDataSource.getMovie(id)) {
            is ApiResult.Success -> {
                val movie = apiResult.data.toDomainModel()
                Result.Success(movie)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Result.Failure()
            }
        }


    override suspend fun getMovieList(): Result<List<Movie>> =
        when (val apiResult = remoteDataSource.getMovieList()) {
            is ApiResult.Success -> {
                val movieList = apiResult.data.items.map { it.toDomainModel() }
                Result.Success(movieList)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Result.Failure()
            }
        }


    override fun getMovieListDao(): LiveData<List<MovieEntityModel>> =
        localDataSource.getMovieListDao()

    override fun getMovieDao(id: Int): LiveData<MovieEntityModel> =
        localDataSource.getMovieDao(id)

    override suspend fun insertMovie(movie: MovieEntityModel) =
        localDataSource.insert(movie)

}