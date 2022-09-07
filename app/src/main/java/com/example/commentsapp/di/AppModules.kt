package com.example.commentsapp.di

import androidx.room.Room
import com.example.commentsapp.CommentApplication
import com.example.commentsapp.data.database.CommentsDatabase
import com.example.commentsapp.data.repository.CommentRepositoryImpl
import com.example.commentsapp.domain.repository.CommentRepository
import com.example.commentsapp.presentation.viewmodel.AddCommentViewModel
import com.example.commentsapp.presentation.viewmodel.CommentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModules = module {
    // data
    single { CommentApplication.INSTANCE }
    single {
        Room.databaseBuilder(
            androidContext(),
            CommentsDatabase::class.java,
            "comments_database"
        ).build()
    }

    factory { (get() as CommentsDatabase).commentsDao() }
    // domain
    factory<CommentRepository> { CommentRepositoryImpl(get()) }

    // UI
    viewModel { CommentViewModel(get()) }
    viewModel { AddCommentViewModel(get()) }
}

