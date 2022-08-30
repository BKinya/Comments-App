package com.example.commentsapp.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dataLocal.entities.CommentsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {
  @Insert
  suspend fun saveComment(comment: CommentsModel)

  @Query("SELECT * FROM comment")
  fun getComments(): Flow<List<CommentsModel>>
}