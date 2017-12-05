package com.limayeneha.twitter_trov.viewmodel

import android.databinding.BaseObservable
import com.limayeneha.twitter_trov.model.User

/**
 * Created by limayeneha on 10/26/17.
 */

class UserLoginViewModel(private var user: User?) : BaseObservable() {

  val userName: String
    get() = user!!.username

  fun setUser(user: User) {
    this.user = user
    notifyChange()
  }
}
