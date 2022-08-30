package com.example.commentsapp.domain.usecases

import com.example.commentsapp.domain.repository.CommentRepository

class SaveCommentUseCase(
  private val commentRepository: CommentRepository
) {

  suspend fun saveComment(message: String){
    commentRepository.saveComment(message)
  }
}