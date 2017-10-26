package com.limayeneha.twitter_trov.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.TextView;

import com.limayeneha.twitter_trov.R;
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
        return tweet.tweetText;
    }

    public Date getDatePosted() {
        return tweet.datePosted;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
        notifyChange();
    }
}
