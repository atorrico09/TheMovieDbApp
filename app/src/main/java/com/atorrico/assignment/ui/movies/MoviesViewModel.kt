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

    val movies_favourites = repository.getAllMovies()

    fun fetchMovies() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try{
            val listMoviesWithGenre = repository.getMovies().mapSuccess {
                val list: MutableList<MovieWithGenre> = arrayListOf()
                it.items.forEach { movie ->
                    repository.getGenre(movie.genre_ids[0]).mapSuccess { genre_name ->
                        list.add(MovieWithGenre(movie.id, movie.title, genre_name, movie.backdrop_path))
                    }
                }
                list
            }
            emit(Result.Success(listMoviesWithGenre))
        }catch (e: Exception){
//            emit(Result.Failure(e))
        }
    }
}
