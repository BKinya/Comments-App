package com.example.commentsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commentsapp.domain.repository.CommentRepository
import com.example.commentsapp.presentation.intent.AddCommentsIntent
import com.example.commentsapp.presentation.intent.AddCommentsIntent.SaveComment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddCommentViewModel(
    private val commentRepository: CommentRepository
): ViewModel() {

    val addCommentsIntent = Channel<AddCommentsIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch {
            addCommentsIntent.consumeAsFlow().collect{ intent ->
                when(intent){
                    is SaveComment -> saveComment(intent)
                }
            }
        }
    }

    private fun saveComment(intent: AddCommentsIntent) {
        val message = (intent as SaveComment).comment
        viewModelScope.launch {
            commentRepository.saveComment(message)
        }
    }
}