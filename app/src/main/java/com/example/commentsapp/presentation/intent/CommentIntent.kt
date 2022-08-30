package com.example.commentsapp.presentation.intent

import com.example.commentsapp.domain.model.Comment

sealed class CommentIntent {
    object FetchComments: CommentIntent()
    data class SaveComment(val comment: String): CommentIntent()
    data class EditComment(val comment: Comment): CommentIntent()
    data class DeleteComment(val id: Int): CommentIntent()
    object NavigateToAddComment: CommentIntent()
    object NavigateToCommentsList: CommentIntent()// I doubt it usefulness
}