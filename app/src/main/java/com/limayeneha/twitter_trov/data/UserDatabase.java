package com.limayeneha.twitter_trov.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by limayeneha on 5/27/17.
 */
@Database(name = UserDatabase.NAME, version = UserDatabase.VERSION)
public class UserDatabase {
    public static final String NAME = "UserDatabase";

    public static final int VERSION = 1;
}
