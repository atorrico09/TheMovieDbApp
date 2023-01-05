package com.atorrico.assignment.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_favourites")
data class MovieEntityModel (
    @PrimaryKey
    val id: Int,
    val poster_path: String,
    var subscribe: Boolean
)