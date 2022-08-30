package com.example.commentsapp.di

import com.example.commentsapp.CommentApplication
import com.example.commentsapp.presentation.viewmodel.CommentViewModel
import com.example.dataLocal.database.CommentsDatabase
import com.example.commentsapp.data.repository.CommentRepositoryImpl
import com.example.commentsapp.domain.repository.CommentRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModules = module {
  // data
  single { CommentApplication.INSTANCE }
  single { CommentsDatabase.getinstance(get()) }
  factory { (get() as CommentsDatabase).commentsDao() }
  // domain
  factory<CommentRepository> { CommentRepositoryImpl(get()) }

  // UI
  viewModel { CommentViewModel(get()) }
}

