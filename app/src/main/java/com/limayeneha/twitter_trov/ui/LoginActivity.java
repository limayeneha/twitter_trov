package com.limayeneha.twitter_trov.ui;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.databinding.ActivityLoginBinding;
import com.limayeneha.twitter_trov.model.User;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;
    LinearLayout signInLinearLayout;
    Button signInButton;
    EditText email;
    EditText password;
    String username;
    String pwd;

    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    private Matcher matcher;

    CompositeDisposable compositeSubscription = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        emailInputLayout = binding.emailTil;
        passwordInputLayout = binding.passwordTil;
        signInLinearLayout = binding.signInLl;
        signInButton = binding.signInBtn;
        email = binding.emailEt;
        password = binding.passwordEt;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sharedPref.getBoolean(getResources().getString(R.string.isLogin), false);
        if (isLogin) {
            this.startActivity(TweetsListActivity.launchTweetsList(LoginActivity.this,
                    new User(1234, getResources().getString(R.string.username))));
            finish();
        }

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(email);
        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(password);

        // Checks for validity of the email input field

        Disposable emailSubscription = emailChangeObservable
                .doOnNext(charSequence ->
                        hideEmailError()
                )
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence ->
                        !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe((charSequence ->
                {
                    boolean isEmailValid = validateEmail(charSequence.toString());
                    if (!isEmailValid) {
                        showEmailError();
                    } else {
                        hideEmailError();
                    }
                }), e -> e.printStackTrace());

        compositeSubscription.add(emailSubscription);

        // Checks for validity of the password input field

        Disposable passwordSubscription = passwordChangeObservable
                .doOnNext(charSequence ->
                        hidePasswordError())
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe((charSequence -> {
                    boolean isPasswordValid = validatePassword(charSequence.toString());
                    if (!isPasswordValid) {
                        showPasswordError();
                    } else {
                        hidePasswordError();
                    }
                }), (e -> e.printStackTrace()));


        compositeSubscription.add(passwordSubscription);

        // Checks for validity of all input fields

        Disposable signInFieldsSubscription = Observable.combineLatest(emailChangeObservable, passwordChangeObservable,
                ((email, password) -> {
                    boolean isEmailValid = validateEmail(email.toString());
                    boolean isPasswordValid = validatePassword(password.toString());

                    return isEmailValid && isPasswordValid;
                }))
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe((validFields -> {
                    if (validFields) {
                        enableSignIn();
                    } else {
                        disableSignIn();
                    }
                }), e -> e.printStackTrace());

        compositeSubscription.add(signInFieldsSubscription);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.dispose();
    }
    // endregion

    // region Helper Methods
    private void enableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void disableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.GONE);
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email))
            return false;

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void showEmailError() {
        enableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(true);
        emailInputLayout.setError(getString(R.string.invalid_email));
    }

    private void hideEmailError() {
        disableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(false);
        emailInputLayout.setError(null);
    }

    private void showPasswordError() {
        enableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(true);
        passwordInputLayout.setError(getString(R.string.invalid_password));
    }

    private void hidePasswordError() {
        disableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(false);
        passwordInputLayout.setError(null);
    }

    private void enableSignIn() {
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        signInButton.setEnabled(true);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        signInButton.setOnClickListener(v -> onSignInButtonClick());
    }

    private void disableSignIn() {
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        signInButton.setEnabled(false);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void onSignInButtonClick() {


        if (email != null)
            username = email.getText().toString();
        if (password != null) pwd = password.getText().toString();
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
