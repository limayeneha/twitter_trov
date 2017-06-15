package com.limayeneha.twitter_trov.model;

import com.limayeneha.twitter_trov.data.TweetsDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by limayeneha on 5/27/17.
 */
@Table(database = TweetsDatabase.class)
public class Tweet extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }



    @Column
    public String tweetText;

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    @Column
    public Date datePosted;

    public Tweet() {

    }

    public Tweet(String tweetText, int userId, Date datePosted) {
        this.tweetText = tweetText;
        this.userId = userId;
        this.datePosted = datePosted;
    }

    public static Comparator<Tweet> datePostedComparator = new Comparator<Tweet>() {
        @Override
        public int compare(Tweet p1, Tweet p2) {
            return p2.datePosted.compareTo(p1.datePosted);
        }
    };
}
