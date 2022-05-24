package com.example.commentsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commentsapp.databinding.CommentLayoutBinding
import com.example.domain.model.Comment

class CommentAdapter(
 private val comments: List<Comment>
): RecyclerView.Adapter<CommentViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
    val binding = CommentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CommentViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
    val message = comments[position].message
    holder.commentTextview.text = message
  }

  override fun getItemCount(): Int {
   return comments.size
  }
}