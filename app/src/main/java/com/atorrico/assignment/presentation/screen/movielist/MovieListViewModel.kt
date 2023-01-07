package com.atorrico.assignment.presentation.screen.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.atorrico.assignment.domain.usecase.GetMovieListUseCase
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.mapSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    fun getMovieList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val movieList = getMovieListUseCase().mapSuccess { it.items }
            emit(Result.Success(movieList))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getMovieListDao() = getMovieListUseCase.getMovieListDao()
}
