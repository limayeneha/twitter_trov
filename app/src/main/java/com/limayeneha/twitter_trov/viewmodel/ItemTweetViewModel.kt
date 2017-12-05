package com.limayeneha.twitter_trov.viewmodel

import android.databinding.BaseObservable

import com.limayeneha.twitter_trov.model.Tweet

import java.util.Date

/**
 * Created by limayeneha on 10/18/17.
 */

class ItemTweetViewModel(private var tweet: Tweet) : BaseObservable() {

  val tweetText: String
    get() = tweet.tweetText

  val datePosted: Date?
    get() = tweet.datePosted

  fun setTweet(tweet: Tweet) {
    this.tweet = tweet
    notifyChange()
  }
}
