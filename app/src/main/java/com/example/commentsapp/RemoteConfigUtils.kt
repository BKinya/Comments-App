package com.example.commentsapp

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import logcat.logcat

/**
 *
 */
object RemoteConfigUtils {
    private val TAG = "RemoteConfigUtils"

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init(){
        remoteConfig = getRemoteConfig()
    }

    /**
     *
     */
    private fun getRemoteConfig(): FirebaseRemoteConfig{
        remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            if (BuildConfig.DEBUG) {
                minimumFetchIntervalInSeconds = 0 // Kept 0 for quick debug
            } else {
                minimumFetchIntervalInSeconds = 60 * 60 // Change this based on your requirement
            }
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            logcat(TAG){"addOnCompleteListener"}
        }

        return remoteConfig
    }

    /**
     *
     */
    fun getButtonColor() = remoteConfig.getString("button_color")

    /**
     *
     */
    fun getSaveButtonText() = remoteConfig.getString("save_button_text")

    /**
     *
     */
    fun getAddButtonText() = remoteConfig.getString("add_button_text")
}