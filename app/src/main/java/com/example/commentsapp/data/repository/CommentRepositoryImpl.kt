package com.example.dataLocal.repository

import com.example.commentsapp.data.dao.CommentsDao
import com.example.dataLocal.entities.CommentsModel
import com.example.commentsapp.domain.model.Comment
import com.example.commentsapp.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow

class CommentRepositoryImpl(
  private val commentsDao: CommentsDao
): CommentRepository {
  override suspend fun saveComment(message: String) {
    val entity = CommentsModel(
      id = null,
      message = message
    )
   commentsDao.saveComment(entity)
  }

  override fun getComments(): Flow<List<Comment>> {
    return commentsDao.getComments()
  }
}