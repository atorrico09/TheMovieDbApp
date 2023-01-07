package com.atorrico.assignment.domain.model

data class Movie(
    val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
)
