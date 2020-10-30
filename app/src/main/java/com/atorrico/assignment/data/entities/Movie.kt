package com.atorrico.assignment.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "movies")
data class Movie(
    val poster_path: String,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
//    val media_type : String,
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    //LOCAL
    var subscribe: Boolean = false
){
    class Converters {

        @TypeConverter
        fun listToJson(value: List<Int>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
    }
}