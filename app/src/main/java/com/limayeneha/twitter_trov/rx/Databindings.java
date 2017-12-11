package com.limayeneha.twitter_trov.rx;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import io.reactivex.Observable;
import java.util.List;
import java.util.Objects;

/**
 * Created by nehalimaye on 12/3/17.
 */

@SuppressWarnings("PMD.UseUtilityClass")
public class Databindings {

    /**
     * Convert an ObservableField to an Rx Observable.
     *
     * This version will emit the current value of the ObservableField when subscribed.
     *
     * Note: Null values are not emitted.
     *
     * @param observableField The ObservableField to listen on.
     * @param <T> Templated type for ObservableField
     * @return An Rx Observable that will emit events on any change.
     */
    public static <T> Observable<T> toObservable(
            @NonNull final android.databinding.ObservableField<T> observableField) {
        return toObservable(observableField, true);
    }

    /**
     * Convert an ObservableField to an Rx Observable.
     *
     * This version will optionally emit the current value of the ObservableField when subscribed, if replay is true.
     *
     * Note: Null values are not emitted.
     *
     * @param observableField The ObservableField to listen on.
     * @param replay If true, the initial value of the ObservableField will be emitted on subscribe.
     * @param <T> Templated type for ObservableField
     * @return An Rx Observable that will emit events on any change.
     */
    public static <T> Observable<T> toObservable(@NonNull final android.databinding.ObservableField<T> observableField,
            boolean replay) {
        return Observable.create(emitter -> {
            if (!emitter.isDisposed() && replay && observableField.get() != null) {
                emitter.onNext(observableField.get());
            }

            final android.databinding.Observable.OnPropertyChangedCallback callback =
                    new android.databinding.Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(android.databinding.Observable dataBindingObservable,
                                int propertyId) {
                            if (!emitter.isDisposed()
                                    && Objects.equals(dataBindingObservable, observableField)
                                    && observableField.get() != null) {
                                emitter.onNext(observableField.get());
                            }
                        }
                    };

            observableField.addOnPropertyChangedCallback(callback);

            emitter.setCancellable(() -> observableField.removeOnPropertyChangedCallback(callback));
        });
    }
}
