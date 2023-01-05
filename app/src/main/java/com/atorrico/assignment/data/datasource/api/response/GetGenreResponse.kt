package com.atorrico.assignment.data.datasource.api.response

import com.atorrico.assignment.data.datasource.api.model.GenreApiModel

data class GetGenreResponse(
    val genres: List<GenreApiModel>
)
