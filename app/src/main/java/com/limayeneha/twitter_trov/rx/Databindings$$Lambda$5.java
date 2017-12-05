package com.limayeneha.twitter_trov.rx;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableList;
import io.reactivex.functions.Cancellable;

/**
 * Created by nehalimaye on 12/3/17.
 */

final class Databindings$$Lambda$5 implements Cancellable {
    private final ObservableList arg$1;
    private final ObservableList.OnListChangedCallback arg$2;

    private Databindings$$Lambda$5(ObservableList var1, ObservableList.OnListChangedCallback var2) {
        this.arg$1 = var1;
        this.arg$2 = var2;
    }

    public void cancel() {
        Databindings.lambda$null$2(this.arg$1, this.arg$2);
    }

    public static Cancellable lambdaFactory$(ObservableList var0, ObservableList.OnListChangedCallback var1) {
        return new Databindings$$Lambda$5(var0, var1);
    }
}
