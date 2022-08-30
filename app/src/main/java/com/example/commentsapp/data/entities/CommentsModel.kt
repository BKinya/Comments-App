package com.example.dataLocal.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.commentsapp.domain.model.Comment

@Entity(tableName = "comment")
data class CommentsModel(
  @PrimaryKey(autoGenerate = true)
  override var id: Int?,
  override val message: String
) : Comment()