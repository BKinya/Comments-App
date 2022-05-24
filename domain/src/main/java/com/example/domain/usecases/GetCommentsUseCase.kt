package com.example.domain.usecases

import com.example.domain.repository.CommentRepository

class GetCommentsUseCase(
  private val commentRepository: CommentRepository
) {

  fun getComments() = commentRepository.getComments()
}