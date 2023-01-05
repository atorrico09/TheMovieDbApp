package com.atorrico.assignment.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.atorrico.assignment.data.entities.MovieWithGenre
import com.atorrico.assignment.data.repository.MovieRepository
import com.atorrico.assignment.utils.Result
import com.atorrico.assignment.utils.mapSuccess
import kotlinx.coroutines.Dispatchers

class MoviesViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val moviesFavourites = repository.getAllMovies()

    fun fetchMovies() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            val listMoviesWithGenre = repository.getMovies().mapSuccess { movies ->
                val list: MutableList<MovieWithGenre> = arrayListOf()
                var genreMap: Map<Int, String> = mapOf()

                repository.getGenres().mapSuccess { genres ->
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
}
