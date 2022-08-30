package com.example.commentsapp.presentation

import android.app.Activity
import com.example.commentsapp.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import logcat.logcat


object RemoteConfigUtils {

    private lateinit var remoteConfig: FirebaseRemoteConfig
    private val TAG = "RemoteConfigUtils"

    /**
     * Get [FirebaseRemoteConfig] instance
     * set the minimum fetch interval to allow for frequent refreshes
     * Set in-app default parameter values
     *
     */
    fun setUpRemoteConfig() {
        remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    /**
     * Fetch and activate remote configs
     */
    fun fetchRemoteConfigs(activity: Activity) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    logcat(TAG) { "Config params updated: $updated" }
                } else {
                    logcat(TAG) { "Config params failed" }
                }
            }
    }

    fun getButtonColor() = remoteConfig.getString("button_color")

    fun getSaveButtonText() = remoteConfig.getString("save_button_text")

    fun getAddButtonText() = remoteConfig.getString("add_button_text")

    fun getTitleText() = remoteConfig.getString("title_text")

    fun getInputLabel() = remoteConfig.getString("input_label")

    fun getHintText() = remoteConfig.getString("hint_text")

    fun getIsNotes() = remoteConfig.getBoolean("is_notes")
}