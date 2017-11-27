package com.limayeneha.twitter_trov.data

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by limayeneha on 5/27/17.
 */

@Database(name = TweetsDatabase.NAME, version = TweetsDatabase.VERSION)
object TweetsDatabase {
    const val NAME: String = "TweetsDatabase"

    const val VERSION: Int = 1
}
