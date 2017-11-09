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
import com.limayeneha.twitter_trov.databinding.ActivityLoginBinding;
import com.limayeneha.twitter_trov.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    EditText etEmail;
    EditText etPassword;
    TextView tvEmail;
    TextView tvPassword;
    Button signInButton;
    String username;
    String pwd;

    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        signInButton = binding.signInBtn;
        tvEmail = binding.tvEmail;
        tvPassword = binding.tvPassword;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sharedPref.getBoolean(getResources().getString(R.string.isLogin), false);
        if (isLogin) {
            this.startActivity(TweetsListActivity.launchTweetsList(LoginActivity.this,
                    new User(1234, getResources().getString(R.string.username))));
            finish();
        }

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(etEmail);
        emailChangeObservable
                .map(this::validateEmail)
                .subscribe(isValid -> {
                    if (isValid || TextUtils.isEmpty(etEmail.getText())) {
                        tvEmail.setVisibility(View.GONE);
                    } else {
                        tvEmail.setVisibility(View.VISIBLE);
                        tvEmail.setText(getString(R.string.invalid_email));
                    }
                });


        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(etPassword);
        passwordChangeObservable
                .map(this::validatePassword)
                .subscribe(isValid -> {
                    if (isValid || TextUtils.isEmpty(etPassword.getText())) {
                        tvPassword.setVisibility(View.GONE);
                    } else {
                        tvPassword.setVisibility(View.VISIBLE);
                        tvPassword.setText(getString(R.string.invalid_password));
                    }
                });

        Observable<Boolean> combinedObservables = Observable.combineLatest(emailChangeObservable, passwordChangeObservable,
                (o1, o2) -> validateEmail(o1) && validatePassword(o2));
        combinedObservables.subscribe(
                (validFields -> {
                    if (validFields) {
                        enableSignIn();
                    } else {
                        disableSignIn();
                    }
                })
        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    // endregion

    // region Helper Methods

    private boolean validateEmail(CharSequence email) {
        if (TextUtils.isEmpty(email))
            return false;

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(CharSequence password) {
        return password.length() > 5;
    }

    private void enableSignIn() {
        signInButton.setEnabled(true);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        signInButton.setOnClickListener(v -> onSignInButtonClick());
    }

    private void disableSignIn() {
        signInButton.setEnabled(false);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void onSignInButtonClick() {
        if (etEmail != null)
            username = etEmail.getText().toString();
        if (etPassword != null) pwd = etPassword.getText().toString();
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
