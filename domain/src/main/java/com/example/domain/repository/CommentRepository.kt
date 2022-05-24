package com.example.domain.repository

import com.example.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
  suspend fun saveComment(message: String)

  fun getComments(): Flow<List<Comment>>
}