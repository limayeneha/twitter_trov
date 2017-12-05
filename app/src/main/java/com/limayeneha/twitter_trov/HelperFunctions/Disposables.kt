package com.limayeneha.twitter_trov.HelperFunctions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by nehalimaye on 12/1/17.
 */

fun Disposable.addTo(composite: CompositeDisposable) {
  composite.add(this)
}


