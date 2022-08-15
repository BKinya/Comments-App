package com.example.commentsapp

import android.app.Application
import com.example.commentsapp.di.diModules
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import logcat.logcat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CommentApplication: Application() {

  companion object{
    lateinit var INSTANCE: CommentApplication
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this

    // Log in debug builds, no-op in release builds.
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.DEBUG)

    // Start Koin
    startKoin {
      androidLogger()
      androidContext(this@CommentApplication)
      modules(diModules)
    }
  }
}