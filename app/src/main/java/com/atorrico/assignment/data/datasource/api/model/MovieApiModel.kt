package com.atorrico.assignment.data.datasource.api.model

import com.atorrico.assignment.domain.model.Movie

data class MovieApiModel(
    val id: Int,
    val poster_path: String,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
) {
    fun toDomainModel(): Movie {
        return Movie(
            id = id,
            posterPath = poster_path,
            backdropPath = backdrop_path,
            title = title,
            overview = overview,
            releaseDate = release_date
        )
    }
}