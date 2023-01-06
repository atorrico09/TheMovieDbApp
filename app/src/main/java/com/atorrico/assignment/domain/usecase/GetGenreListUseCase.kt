package com.atorrico.assignment.domain.usecase

import com.atorrico.assignment.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.getGenreList()
}