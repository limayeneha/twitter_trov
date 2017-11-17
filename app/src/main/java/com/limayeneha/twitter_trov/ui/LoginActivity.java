package com.limayeneha.twitter_trov.ui;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.TwitterTrovApplication;
import com.limayeneha.twitter_trov.databinding.ActivityLoginBinding;
import com.limayeneha.twitter_trov.model.User;
import com.limayeneha.twitter_trov.viewmodel.LoginViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences sharedPref;

    CompositeDisposable disposables = new CompositeDisposable();

    Observable<CharSequence> emailChangeObservable;
    Observable<CharSequence> passwordChangeObservable;

    @NonNull
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        emailChangeObservable = RxTextView.textChanges(binding.etEmail);
        passwordChangeObservable = RxTextView.textChanges(binding.etPassword);

        loginViewModel = getViewModel(emailChangeObservable, passwordChangeObservable);
        binding.setViewModel(loginViewModel);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sharedPref.getBoolean(getResources().getString(R.string.isLogin), false);
        if (isLogin) {
            this.startActivity(TweetsListActivity.launchTweetsList(LoginActivity.this,
                    new User(1234, getResources().getString(R.string.username))));
            finish();
        }

        loginViewModel.bind();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addSubscriptions();
    }

    private void addSubscriptions() {
        disposables.addAll(loginViewModel.isEmailValid
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> showEmailMessage(value)),

                loginViewModel.isPasswordValid
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> showPasswordMessage(value))
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginViewModel.unbound();
    }

    private void showEmailMessage(Boolean show) {
        if (!show || TextUtils.isEmpty(binding.etEmail.getText())) {
            binding.tvEmail.setVisibility(View.GONE);
        } else {
            binding.tvEmail.setVisibility(View.VISIBLE);
            binding.tvEmail.setText(getString(R.string.invalid_email));

        }
    }

    private void showPasswordMessage(Boolean show) {
        if (!show || TextUtils.isEmpty(binding.etPassword.getText())) {
            binding.tvPassword.setVisibility(View.GONE);
        } else {
            binding.tvPassword.setVisibility(View.VISIBLE);
            binding.tvPassword.setText(getString(R.string.invalid_password));

        }
    }

    @NonNull
    private LoginViewModel getViewModel(Observable<CharSequence> emailChangeObservable, Observable<CharSequence> passwordChangeObservable) {
        return ((TwitterTrovApplication) getApplication()).getViewModel(emailChangeObservable, passwordChangeObservable);
    }


    public void onSignInButtonClick(View view) {
        String username ="";
        String pwd ="";
        if (binding.etEmail != null)
            username = binding.etEmail.getText().toString();
        if (binding.etPassword != null) pwd = binding.etPassword.getText().toString();
        if (username.equals(getResources().getString(R.string.username)) && pwd.equals(getResources().getString(R.string.password))) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getResources().getString(R.string.isLogin), true);
            editor.commit();

            LoginActivity.this.startActivity(TweetsListActivity.launchTweetsList(LoginActivity.this,
                    new User(1234, getResources().getString(R.string.username))));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_user), Toast.LENGTH_SHORT).show();
        }
    }
}
