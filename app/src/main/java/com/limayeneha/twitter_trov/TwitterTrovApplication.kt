package com.limayeneha.twitter_trov

import android.app.Application

import com.limayeneha.twitter_trov.viewmodel.LoginViewModel
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager

import io.reactivex.Observable
import io.reactivex.annotations.NonNull

/**
 * Created by limayeneha on 5/27/17.
 */

class TwitterTrovApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // This instantiates DBFlow
        FlowManager.init(FlowConfig.Builder(this).build())
        // add for verbose logging
        // FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }

    override fun onTerminate() {
        super.onTerminate()
        FlowManager.destroy()
    }

    @NonNull
    fun getViewModel(): LoginViewModel {
        return LoginViewModel()
    }
}
