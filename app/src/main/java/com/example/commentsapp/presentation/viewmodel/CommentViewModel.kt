package com.example.commentsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commentsapp.domain.model.Comment
import com.example.commentsapp.domain.usecases.GetCommentsUseCase
import com.example.commentsapp.domain.usecases.SaveCommentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import logcat.logcat

class CommentViewModel(
  private val saveCommentUseCase: SaveCommentUseCase,
  private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

  private val _comments = MutableStateFlow<List<Comment>>(emptyList())
  val comments: StateFlow<List<Comment>>
    get() = _comments

  fun getComments() {
    viewModelScope.launch {
      val result = getCommentsUseCase.getComments()
      result.catch {
            logcat("CommentViewModel - getComments") { "Exception ${it.message}" }
      }
        .collect {
          logcat("CommentViewModel - getComments") { "comments => ${it.size}" }
          _comments.value = it
        }
    }
  }

  fun saveComment(message: String) {
    viewModelScope.launch {
      saveCommentUseCase.saveComment(message)
    }
  }
}