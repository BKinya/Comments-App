package com.example.commentsapp.presentation.model

import com.example.commentsapp.domain.model.Comment

sealed class CommentUiState {
    object NoComments: CommentUiState()
    object Loading: CommentUiState()
    data class Success(val data: List<Comment>): CommentUiState()
    data class Error(val errorMsg: String): CommentUiState()
}