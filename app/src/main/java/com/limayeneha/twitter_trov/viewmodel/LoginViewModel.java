package com.limayeneha.twitter_trov.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by limayeneha on 11/16/17.
 */

public class LoginViewModel extends BaseObservable {

    Observable<CharSequence> passwordChangeObservable;
    Observable<CharSequence> emailChangeObservable;
    Observable<Boolean> combinedObservables;

    @NonNull
    public final BehaviorSubject<Boolean> isEmailValid = BehaviorSubject.create();

    @NonNull
    public final BehaviorSubject<Boolean> isPasswordValid = BehaviorSubject.create();


    public final ObservableBoolean enableSignIn = new ObservableBoolean(false);

    public LoginViewModel(Observable<CharSequence> emailChangeObservable, Observable<CharSequence> passwordChangeObservable) {
        this.emailChangeObservable = emailChangeObservable;
        this.passwordChangeObservable = passwordChangeObservable;
        this.combinedObservables = Observable.combineLatest(emailChangeObservable, passwordChangeObservable,
                (o1, o2) -> validateEmail(o1) && validatePassword(o2));
    }

    CompositeDisposable disposable = new CompositeDisposable();


    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    private Matcher matcher;

    public void bind() {
        disposable.add(emailChangeObservable
                .map(this::validateEmail)
                .subscribe(isValid -> {
                    if (isValid) {
                        isEmailValid.onNext(false);
                    } else {
                        isEmailValid.onNext(true);
                    }
                }));
        disposable.add(passwordChangeObservable
                .map(this::validatePassword)
                .subscribe(isValid -> {
                    if (isValid) {
                        isPasswordValid.onNext(false);
                    } else {
                        isPasswordValid.onNext(true);
                    }
                }));
        disposable.add(combinedObservables
                .subscribe(validFields -> {
                    if (validFields) {
                        enableSignIn.set(true);
                    } else {
                        enableSignIn.set(false);
                    }
                }));
    }

    private boolean validatePassword(CharSequence password) {
        return password.length() > 5;
    }

    private boolean validateEmail(CharSequence email) {
        if (TextUtils.isEmpty(email))
            return false;

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void unbound() {
        disposable.clear();
    }

}
