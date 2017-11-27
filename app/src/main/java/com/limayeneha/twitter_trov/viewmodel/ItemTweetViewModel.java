package com.limayeneha.twitter_trov.viewmodel;

import android.databinding.BaseObservable;

import com.limayeneha.twitter_trov.model.Tweet;

import java.util.Date;

/**
 * Created by limayeneha on 10/18/17.
 */

public class ItemTweetViewModel extends BaseObservable{

    private Tweet tweet;

    public ItemTweetViewModel(Tweet tweet) {
        this.tweet = tweet;
    }

    public String getTweetText() {
        return tweet.getTweetText();
    }

    public Date getDatePosted() {
        return tweet.getDatePosted();
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
        notifyChange();
    }
}
