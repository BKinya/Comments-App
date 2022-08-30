package com.example.commentsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commentsapp.domain.model.Comment
import com.example.commentsapp.domain.usecases.GetCommentsUseCase
import com.example.commentsapp.domain.usecases.SaveCommentUseCase
import com.example.commentsapp.presentation.model.CommentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import logcat.logcat

class CommentViewModel(
  private val saveCommentUseCase: SaveCommentUseCase,
  private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow<CommentUiState>(CommentUiState.NoComments)
  val uiState: StateFlow<CommentUiState>
    get() = _uiState

  fun getComments() {
    viewModelScope.launch {
      val result = getCommentsUseCase.getComments()
      result.catch {
            logcat("CommentViewModel - getComments") { "Exception ${it.message}" }
        _uiState.value = CommentUiState.Error(error = it.message ?: "Something went wrong!")
      }
        .collect {comments ->
          logcat("CommentViewModel - getComments") { "comments => ${comments.size}" }
          if (!comments.isEmpty()){
            _uiState.value = CommentUiState.Success(data = comments)
          }
        }
    }
  }

  fun saveComment(message: String) {
    viewModelScope.launch {
      saveCommentUseCase.saveComment(message)
    }
  }
}