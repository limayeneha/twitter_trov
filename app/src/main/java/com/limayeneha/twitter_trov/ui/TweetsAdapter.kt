package com.limayeneha.twitter_trov.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.limayeneha.twitter_trov.R

import com.limayeneha.twitter_trov.databinding.TweetsListViewBinding
import com.limayeneha.twitter_trov.model.Tweet
import com.limayeneha.twitter_trov.viewmodel.ItemTweetViewModel

import java.util.ArrayList

/**
 * Created by limayeneha on 2/9/17.
 */

class TweetsAdapter : RecyclerView.Adapter<TweetsAdapter.PViewHolder>() {

  private val tweets = ArrayList<Tweet>()

  inner class PViewHolder(private val mTweetsListViewBinding: TweetsListViewBinding) : RecyclerView.ViewHolder(
      mTweetsListViewBinding.itemTweet) {

    internal fun bindTweet(tweet: Tweet) {
      if (mTweetsListViewBinding.tweetViewModel == null) {
        mTweetsListViewBinding.tweetViewModel = ItemTweetViewModel(tweet)
      } else {
        mTweetsListViewBinding.tweetViewModel.setTweet(tweet)
      }
    }
  }

  fun setTweets(newTweets: List<Tweet>) {
    tweets.clear()
    tweets.addAll(newTweets)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
    val itemTweetBinding = DataBindingUtil.inflate<TweetsListViewBinding>(LayoutInflater.from(parent.context),
        R.layout.tweets_list_view,
        parent, false)
    return PViewHolder(itemTweetBinding)
  }

  override fun onBindViewHolder(holder: PViewHolder, position: Int) {
    holder.bindTweet(tweets[position])
  }

  override fun getItemCount(): Int {
    return tweets.size
  }


}
