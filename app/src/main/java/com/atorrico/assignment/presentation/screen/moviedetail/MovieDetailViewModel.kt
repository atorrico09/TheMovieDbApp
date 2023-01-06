package com.atorrico.assignment.presentation.screen.moviedetail

import androidx.lifecycle.*
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.domain.usecase.GetMovieUseCase
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.mapSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private var id: Int = 0

    fun init(id: Int) {
        this.id = id
    }

    fun getMovie() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val movie = getMovieUseCase(id).mapSuccess { it }
            emit(Result.Success(movie))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    suspend fun insertMovieDao (movie: MovieEntityModel) = getMovieUseCase.insertMovieDao(movie)

    fun getMovie(id: Int) = getMovieUseCase.getMovieDao(id)

}
