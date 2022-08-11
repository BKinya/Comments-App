package com.example.commentsapp

import android.app.Application
import com.example.commentsapp.di.diModules
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import logcat.logcat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CommentApplication: Application() {

  companion object{
    lateinit var INSTANCE: CommentApplication
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this
    logcat("APPPPP"){"When is this called?"}

    // initialize firebase remote configs
    RemoteConfigUtils.init()


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