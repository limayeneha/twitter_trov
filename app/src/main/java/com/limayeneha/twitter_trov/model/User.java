package com.limayeneha.twitter_trov.model;

import com.limayeneha.twitter_trov.data.UserDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by limayeneha on 5/27/17.
 */
@Table(database = UserDatabase.class)
public class User extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String username;

    @Column
    public String password;

    public User() {

    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }


}
