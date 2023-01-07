package com.atorrico.assignment.presentation.screen.moviedetail

import androidx.lifecycle.*
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel
import com.atorrico.assignment.domain.usecase.GetMovieUseCase
import com.atorrico.assignment.presentation.util.Result
import com.atorrico.assignment.presentation.util.mapSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    fun getMovie(id: Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val movie = getMovieUseCase(id).mapSuccess { it }
            emit(Result.Success(movie))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun insertMovieDao (movie: MovieEntityModel) = viewModelScope.launch(Dispatchers.IO) {
        getMovieUseCase.insertMovieDao(movie)
    }

    fun getMovieDao(id: Int) = getMovieUseCase.getMovieDao(id)

}
