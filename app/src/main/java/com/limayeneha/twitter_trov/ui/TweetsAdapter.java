package com.limayeneha.twitter_trov.ui;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limayeneha.twitter_trov.R;

import com.limayeneha.twitter_trov.databinding.TweetsListViewBinding;
import com.limayeneha.twitter_trov.model.Tweet;
import com.limayeneha.twitter_trov.viewmodel.ItemTweetViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limayeneha on 2/9/17.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.PViewHolder> {

    private List<Tweet> tweets = new ArrayList<Tweet>();
    private final Context mContext;

    public class PViewHolder extends RecyclerView.ViewHolder {
        private TweetsListViewBinding mTweetsListViewBinding;

        public PViewHolder(TweetsListViewBinding tweetsListViewBinding) {
            super(tweetsListViewBinding.itemTweet);
            this.mTweetsListViewBinding = tweetsListViewBinding;
        }

        void bindTweet(Tweet tweet) {
            if (mTweetsListViewBinding.getTweetViewModel() == null) {
                mTweetsListViewBinding.setTweetViewModel(
                        new ItemTweetViewModel(tweet));
            } else {
                mTweetsListViewBinding.getTweetViewModel().setTweet(tweet);
            }
        }
    }

    public TweetsAdapter(Context context) {
        mContext = context;
    }

    public void setTweets(List<Tweet> newTweets) {
        tweets.clear();
        tweets.addAll(newTweets);
        notifyDataSetChanged();
    }

    @Override
    public PViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TweetsListViewBinding itemTweetBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.tweets_list_view,
                        parent, false);
        return new PViewHolder(itemTweetBinding);
    }

    @Override
    public void onBindViewHolder(PViewHolder holder, int position) {
        holder.bindTweet(tweets.get(position));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


}
