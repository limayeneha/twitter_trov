package com.limayeneha.twitter_trov.rx;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import io.reactivex.functions.Cancellable;

/**
 * Created by nehalimaye on 12/3/17.
 */

final class Databindings$$Lambda$6 implements Cancellable {
    private final ObservableField arg$1;
    private final Observable.OnPropertyChangedCallback arg$2;

    private Databindings$$Lambda$6(ObservableField var1, Observable.OnPropertyChangedCallback var2) {
        this.arg$1 = var1;
        this.arg$2 = var2;
    }

    public void cancel() {
        Databindings.lambda$null$0(this.arg$1, this.arg$2);
    }

    public static Cancellable lambdaFactory$(ObservableField var0, Observable.OnPropertyChangedCallback var1) {
        return new Databindings$$Lambda$6(var0, var1);
    }
}
