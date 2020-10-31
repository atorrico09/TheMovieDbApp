package com.atorrico.assignment.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_favourites")
data class MovieFavourite (
    @PrimaryKey
    val id: Int,
    val poster_path: String,
    var subscribe: Boolean
)