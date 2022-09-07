package com.example.commentsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.commentsapp.data.dao.CommentsDao
import com.example.commentsapp.data.entities.CommentsModel

@Database(
  entities = [CommentsModel::class],
  version = 1
)
abstract class CommentsDatabase : RoomDatabase() {
  abstract fun commentsDao(): CommentsDao
}