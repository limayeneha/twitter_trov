package com.limayeneha.twitter_trov.rx;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

/**
 * Created by nehalimaye on 12/3/17.
 */

final class Databindings$$Lambda$4 implements Cancellable {
    private final BaseObservable arg$1;
    private final Observable.OnPropertyChangedCallback arg$2;

    private Databindings$$Lambda$4(BaseObservable var1, Observable.OnPropertyChangedCallback var2) {
        this.arg$1 = var1;
        this.arg$2 = var2;
    }

    public void cancel() {
        Databindings.lambda$null$4(this.arg$1, this.arg$2);
    }

    public static Cancellable lambdaFactory$(BaseObservable var0, Observable.OnPropertyChangedCallback var1) {
        return new Databindings$$Lambda$4(var0, var1);
    }
}
