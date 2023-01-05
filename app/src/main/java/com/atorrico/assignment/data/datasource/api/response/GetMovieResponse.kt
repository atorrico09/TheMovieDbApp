package com.atorrico.assignment.data.datasource.api.response

import com.atorrico.assignment.data.datasource.api.model.MovieApiModel

data class GetMovieResponse (
    val items: List<MovieApiModel>
)