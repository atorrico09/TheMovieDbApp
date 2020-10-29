package com.atorrico.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atorrico.assignment.data.entities.Movie


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE subscribe = 0")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE subscribe = 1")
    fun getMyMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

}