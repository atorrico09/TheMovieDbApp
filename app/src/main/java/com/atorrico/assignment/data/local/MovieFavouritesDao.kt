package com.atorrico.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atorrico.assignment.data.entities.MovieFavourite


@Dao
interface MovieFavouritesDao {

    @Query("SELECT * FROM movies_favourites WHERE subscribe = 1")
    fun getAllMovies() : LiveData<List<MovieFavourite>>

    @Query("SELECT * FROM movies_favourites WHERE id = :id")
    fun getMovie(id: Int) : LiveData<MovieFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieFavourite)
}