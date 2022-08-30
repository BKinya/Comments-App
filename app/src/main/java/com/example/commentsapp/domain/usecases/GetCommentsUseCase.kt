package com.example.commentsapp.domain.usecases

import com.example.commentsapp.domain.repository.CommentRepository

class GetCommentsUseCase(
  private val commentRepository: CommentRepository
) {

  fun getComments() = commentRepository.getComments()
}