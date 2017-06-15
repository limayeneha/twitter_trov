package com.limayeneha.twitter_trov.data;
import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by limayeneha on 5/27/17.
 */

@Database(name = TweetsDatabase.NAME, version = TweetsDatabase.VERSION)
public class TweetsDatabase {
    public static final String NAME = "TweetsDatabase";

    public static final int VERSION = 1;
}
