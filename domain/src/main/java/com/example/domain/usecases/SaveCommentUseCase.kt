package com.example.domain.usecases

import com.example.domain.model.Comment
import com.example.domain.repository.CommentRepository

class SaveCommentUseCase(
  private val commentRepository: CommentRepository
) {

  suspend fun saveComment(message: String){
    commentRepository.saveComment(message)
  }
}