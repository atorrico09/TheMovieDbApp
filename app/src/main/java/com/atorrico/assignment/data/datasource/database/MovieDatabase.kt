package com.atorrico.assignment.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atorrico.assignment.data.datasource.database.model.MovieEntityModel

@Database(entities = [MovieEntityModel::class], version = 9, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MovieDatabase::class.java, "movies_favourites")
                .fallbackToDestructiveMigration()
                .build()
    }

}