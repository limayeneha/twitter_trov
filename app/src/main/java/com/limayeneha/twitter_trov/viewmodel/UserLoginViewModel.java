package com.limayeneha.twitter_trov.viewmodel;

import android.databinding.BaseObservable;
import com.limayeneha.twitter_trov.model.User;

/**
 * Created by limayeneha on 10/26/17.
 */

public class UserLoginViewModel extends BaseObservable {

    private User user;

    public UserLoginViewModel(User user) {
        this.user = user;
    }

    public String getUserName() {
        return user.username;
    }

    public void setUser(User user) {
        this.user = user;
        notifyChange();
    }
}
