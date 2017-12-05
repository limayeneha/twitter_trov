package com.limayeneha.twitter_trov.rx;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by nehalimaye on 12/3/17.
 */

public class Databindings {
    public Databindings() {
    }

    public static <T> Observable<T> toObservable(@NonNull ObservableField<T> observableField) {
        return toObservable(observableField, true);
    }

    public static <T> Observable<T> toObservable(@NonNull ObservableField<T> observableField, boolean replay) {
        return Observable.create(Databindings$$Lambda$1.lambdaFactory$(replay, observableField));
    }
}
