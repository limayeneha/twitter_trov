package com.limayeneha.twitter_trov.rx;

import android.databinding.ObservableField;
import android.databinding.ObservableList;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by nehalimaye on 12/3/17.
 */

final class Databindings$$Lambda$2 implements ObservableOnSubscribe {
    private final boolean arg$1;
    private final ObservableList arg$2;

    private Databindings$$Lambda$2(boolean var1, ObservableList var2) {
        this.arg$1 = var1;
        this.arg$2 = var2;
    }

    public void subscribe(ObservableEmitter var1) {
        Databindings.lambda$toObservable$3(this.arg$1, this.arg$2, var1);
    }

    public static ObservableOnSubscribe lambdaFactory$(boolean var0, ObservableList var1) {
        return new Databindings$$Lambda$2(var0, var1);
    }
}