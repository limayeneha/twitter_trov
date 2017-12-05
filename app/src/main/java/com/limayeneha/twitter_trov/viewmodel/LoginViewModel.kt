package com.limayeneha.twitter_trov.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import com.limayeneha.twitter_trov.HelperFunctions.addTo

/**
 * Created by limayeneha on 11/16/17.
 */

class LoginViewModel(internal var emailChangeObservable: Observable<CharSequence>,
    internal var passwordChangeObservable: Observable<CharSequence>) : BaseObservable() {
  internal var combinedObservables: Observable<Boolean>

  @NonNull
  val isEmailValid = BehaviorSubject.create<Boolean>()

  @NonNull
  val isPasswordValid = BehaviorSubject.create<Boolean>()


  val enableSignIn = ObservableBoolean(false)

  internal var disposable = CompositeDisposable()


  private val pattern = Patterns.EMAIL_ADDRESS
  private lateinit var matcher: Matcher

  init {
    this.combinedObservables = Observable.combineLatest(emailChangeObservable,
        passwordChangeObservable,
        BiFunction { o1, o2 -> validateEmail(o1) && validatePassword(o2) })
  }

  fun bind() {
    emailChangeObservable
        .map<Boolean>( { this.validateEmail(it) })
        .subscribe { isValid ->
          if (isValid) {
            isEmailValid.onNext(false)
          } else {
            isEmailValid.onNext(true)
          }
        }.addTo(disposable)
    passwordChangeObservable
        .map<Boolean>({ this.validatePassword(it) })
        .subscribe { isValid ->
          if (isValid) {
            isPasswordValid.onNext(false)
          } else {
            isPasswordValid.onNext(true)
          }
        }.addTo(disposable)
    combinedObservables
        .subscribe { validFields ->
          if (validFields) {
            enableSignIn.set(true)
          } else {
            enableSignIn.set(false)
          }
        }.addTo(disposable)
  }

  private fun validatePassword(password: CharSequence): Boolean {
    return password.length > 5
  }

  private fun validateEmail(email: CharSequence): Boolean {
    if (TextUtils.isEmpty(email))
      return false

    matcher = pattern.matcher(email)
    return matcher.matches()
  }

  fun unbound() {
    disposable.clear()
  }

}
