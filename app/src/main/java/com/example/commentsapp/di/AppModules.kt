package com.example.commentsapp.di

import com.example.commentsapp.CommentApplication
import com.example.commentsapp.viewmodel.CommentViewModel
import com.example.dataLocal.database.CommentsDatabase
import com.example.dataLocal.repository.CommentRepositoryImpl
import com.example.domain.repository.CommentRepository
import com.example.domain.usecases.GetCommentsUseCase
import com.example.domain.usecases.SaveCommentUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModules = module {
  // data
  single { CommentApplication.INSTANCE }
  single { CommentsDatabase.getinstance(get()) }
  factory { (get() as CommentsDatabase).commentsDao() }
  // domain
  factory<CommentRepository> { CommentRepositoryImpl(get()) }
  factory { GetCommentsUseCase(get()) }
  factory { SaveCommentUseCase(get()) }
  // UI
  viewModel { CommentViewModel(get(), get()) }
}

