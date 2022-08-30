package com.example.commentsapp.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.commentsapp.databinding.CommentLayoutBinding

class CommentViewHolder(private val binding: CommentLayoutBinding) :
  RecyclerView.ViewHolder(binding.root) {

    val commentTextview = binding.commentTextView
}