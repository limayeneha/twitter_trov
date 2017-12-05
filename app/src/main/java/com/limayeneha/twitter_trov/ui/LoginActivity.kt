package com.limayeneha.twitter_trov.ui

import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast

import com.jakewharton.rxbinding2.widget.RxTextView
import com.limayeneha.twitter_trov.R
import com.limayeneha.twitter_trov.TwitterTrovApplication
import com.limayeneha.twitter_trov.databinding.ActivityLoginBinding
import com.limayeneha.twitter_trov.model.User
import com.limayeneha.twitter_trov.viewmodel.LoginViewModel

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.limayeneha.twitter_trov.HelperFunctions.addTo

class LoginActivity : AppCompatActivity() {
  internal lateinit var binding: ActivityLoginBinding
  internal lateinit var sharedPref: SharedPreferences

  internal var disposables = CompositeDisposable()

  internal lateinit var emailChangeObservable: Observable<CharSequence>
  internal lateinit var passwordChangeObservable: Observable<CharSequence>

  private lateinit var loginViewModel: LoginViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    emailChangeObservable = RxTextView.textChanges(binding.etEmail)
    passwordChangeObservable = RxTextView.textChanges(binding.etPassword)

    loginViewModel = getViewModel(emailChangeObservable, passwordChangeObservable)
    binding.viewModel = loginViewModel

    sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

    val isLogin = sharedPref.getBoolean(resources.getString(R.string.isLogin), false)
    if (isLogin) {
      this.startActivity(TweetsListActivity.launchTweetsList(this@LoginActivity,
          User(1234, resources.getString(R.string.username))))
      finish()
    }

    loginViewModel.bind()


  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  override fun onResume() {
    super.onResume()
    addSubscriptions()
  }

  private fun addSubscriptions() {
    loginViewModel.isEmailValid
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { value -> showEmailMessage(value) }
        .addTo(disposables)

    loginViewModel.isPasswordValid
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { value -> showPasswordMessage(value) }
        .addTo(disposables)

  }

  override fun onPause() {
    super.onPause()
    loginViewModel.unbound()
  }

  private fun showEmailMessage(show: Boolean) = if ((!show) || TextUtils.isEmpty(binding.etEmail.text)) {
    binding.tvEmail.visibility = View.GONE
  } else {
    binding.tvEmail.visibility = View.VISIBLE
    binding.tvEmail.text = getString(R.string.invalid_email)

  }

  private fun showPasswordMessage(show: Boolean) {
    if ((!show) || binding.etPassword.text.toString().isBlank()) {
      binding.tvPassword.visibility = View.GONE
    } else {
      binding.tvPassword.visibility = View.VISIBLE
      binding.tvPassword.text = getString(R.string.invalid_password)

    }
  }

  @NonNull
  private fun getViewModel(emailChangeObservable: Observable<CharSequence>,
      passwordChangeObservable: Observable<CharSequence>): LoginViewModel {
    return (application as TwitterTrovApplication).getViewModel(emailChangeObservable, passwordChangeObservable)
  }


  fun onSignInButtonClick(view: View) {
    var username = ""
    var pwd = ""
    if (binding.etEmail != null)
      username = binding.etEmail.text.toString()
    if (binding.etPassword != null) pwd = binding.etPassword.text.toString()
    if (username == resources.getString(R.string.username) && pwd == resources.getString(R.string.password)) {
      val editor = sharedPref.edit()
      editor.putBoolean(resources.getString(R.string.isLogin), true)
      editor.commit()

      this@LoginActivity.startActivity(TweetsListActivity.launchTweetsList(this@LoginActivity,
          User(1234, resources.getString(R.string.username))))
      finish()
    } else {
      Toast.makeText(this@LoginActivity, resources.getString(R.string.invalid_user), Toast.LENGTH_SHORT).show()
    }
  }
}
