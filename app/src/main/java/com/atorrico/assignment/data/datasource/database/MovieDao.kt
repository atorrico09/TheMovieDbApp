package com.atorrico.assignment.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_favourites WHERE isSubscribed = 1")
    fun getMovieListDao() : LiveData<List<MovieEntityModel>>

    @Query("SELECT * FROM movies_favourites WHERE id = :id")
    fun getMovieDao(id: Int) : LiveData<MovieEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntityModel)
}