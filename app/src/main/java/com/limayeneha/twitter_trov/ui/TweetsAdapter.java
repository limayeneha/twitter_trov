package com.limayeneha.twitter_trov.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.model.Tweet;

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
    private Activity activity;
    Tweet tweet;
    private final Context mContext;

    public class PViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTweet, tvDate;

        public PViewHolder(View view) {
            super(view);
            tvTweet = (TextView) view.findViewById(R.id.tvTweet);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
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

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweets_list_view, parent, false);

        return new PViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.tvTweet.setText(tweet.getTweetText());
        holder.tvDate.setText(getDatePostedString(tweet.getDatePosted()));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public String getDatePostedString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
        return dateFormat.format(date);

    }

}
