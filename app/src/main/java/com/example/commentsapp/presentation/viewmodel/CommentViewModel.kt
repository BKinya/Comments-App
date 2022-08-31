package com.example.commentsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.commentsapp.domain.model.Comment
import com.example.commentsapp.domain.repository.CommentRepository
import com.example.commentsapp.presentation.intent.AddCommentsIntent
import com.example.commentsapp.presentation.intent.CommentIntent
import com.example.commentsapp.presentation.model.CommentUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import logcat.logcat
import com.example.commentsapp.presentation.intent.CommentIntent.*
import com.example.commentsapp.presentation.view.fragments.CommentsFragmentDirections

class CommentViewModel(
    private val commentRepository: CommentRepository
) : ViewModel() {

    val commentIntent = Channel<CommentIntent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow<CommentUiState>(CommentUiState.NoComments)
    val uiState: StateFlow<CommentUiState>
        get() = _uiState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            commentIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is FetchComments -> getComments()
                    is DeleteComment -> deleteComment(intent)
                    is EditComment -> editComment(intent)
                }
            }
        }
    }

    private fun getComments() {
        viewModelScope.launch {
            val result = commentRepository.getComments()
            result.catch {
                logcat("CommentViewModel - getComments") { "Exception ${it.message}" }
                _uiState.value = CommentUiState.Error(error = it.message ?: "Something went wrong!")
            }
                .collect { comments ->
                    logcat("CommentViewModel - getComments") { "comments => ${comments.size}" }
                    if (comments.isNotEmpty()) {
                        _uiState.value = CommentUiState.Success(data = comments)
                    }
                }
        }
    }



    private fun deleteComment(intent: CommentIntent) {
        val id = (intent as DeleteComment).id
        viewModelScope.launch {
            commentRepository.deleteComment(id)
        }
    }

    private fun editComment(intent: CommentIntent) {
        val comment = (intent as EditComment).comment
        viewModelScope.launch {
            commentRepository.editComment(comment)
        }
    }
}