package com.example.dataLocal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dataLocal.dao.CommentsDao
import com.example.dataLocal.entities.CommentsModel

@Database(
  entities = [CommentsModel::class],
  version = 1
)
abstract class CommentsDatabase : RoomDatabase() {

  companion object {
    @Volatile
    private var INSTANCE: CommentsDatabase? = null

    fun getinstance(context: Context): CommentsDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          CommentsDatabase::class.java,
          "comments_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }

  abstract fun commentsDao(): CommentsDao
}