package com.atorrico.assignment.domain.usecase

import com.atorrico.assignment.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.getMovieList()

    fun getMovieListDao() = movieRepository.getMovieListDao()
}