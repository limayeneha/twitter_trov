package com.limayeneha.twitter_trov.rx;

import android.databinding.ObservableField;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by nehalimaye on 12/3/17.
 */

public class Databindings$$Lambda$1 implements ObservableOnSubscribe {
    private final boolean arg$1;
    private final ObservableField arg$2;

    private Databindings$$Lambda$1(boolean var1, ObservableField var2) {
        this.arg$1 = var1;
        this.arg$2 = var2;
    }

    public void subscribe(ObservableEmitter var1) {
        Databindings.lambda$toObservable$1(this.arg$1, this.arg$2, var1);
    }

    public static ObservableOnSubscribe lambdaFactory$(boolean var0, ObservableField var1) {
        return new Databindings$$Lambda$1(var0, var1);
    }
}
