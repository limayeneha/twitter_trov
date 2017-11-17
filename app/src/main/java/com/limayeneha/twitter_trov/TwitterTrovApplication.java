package com.limayeneha.twitter_trov;

import android.app.Application;

import com.limayeneha.twitter_trov.viewmodel.LoginViewModel;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by limayeneha on 5/27/17.
 */

public class TwitterTrovApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
        // FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }

    @NonNull
    public LoginViewModel getViewModel(Observable<CharSequence> emailChangeObservable, Observable<CharSequence> passwordChangeObservable) {
        return new LoginViewModel(emailChangeObservable, passwordChangeObservable);
    }
}
