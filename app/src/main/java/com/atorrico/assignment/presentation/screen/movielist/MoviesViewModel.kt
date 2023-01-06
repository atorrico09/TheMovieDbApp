package com.atorrico.assignment.presentation.screen.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.atorrico.assignment.data.datasource.api.model.MovieWithGenre
import com.atorrico.assignment.domain.usecase.GetGenreListUseCase
import com.atorrico.assignment.domain.usecase.GetMovieListUseCase
import com.atorrico.assignment.presentation.utils.Result
import com.atorrico.assignment.presentation.utils.mapSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    fun getMovieList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            val listMoviesWithGenre = getMovieListUseCase().mapSuccess { movies ->
                val list: MutableList<MovieWithGenre> = arrayListOf()
                var genreMap: Map<Int, String> = mapOf()

                getGenreListUseCase().mapSuccess { genres ->
                    genreMap = genres.genres.associate { it.id to it.name }
                }

                movies.items.forEach { movie ->
                    list.add(
                        MovieWithGenre(
                            movie.id,
                            movie.title,
                            genreMap[movie.genre_ids.first()].toString(),
                            movie.backdrop_path
                        )
                    )
                }
                list
            }

            emit(Result.Success(listMoviesWithGenre))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getMovieListDao() = getMovieListUseCase.getMovieListDao()
}
