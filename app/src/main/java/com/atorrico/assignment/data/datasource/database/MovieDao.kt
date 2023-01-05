package com.atorrico.assignment.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_favourites WHERE subscribe = 1")
    fun getAllMovies() : LiveData<List<MovieEntityModel>>

    @Query("SELECT * FROM movies_favourites WHERE id = :id")
    fun getMovie(id: Int) : LiveData<MovieEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntityModel)
}