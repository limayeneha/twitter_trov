package com.limayeneha.twitter_trov.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.model.Tweet;
import com.limayeneha.twitter_trov.model.Tweet_Table;
import com.limayeneha.twitter_trov.model.User;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class TweetsListActivity extends AppCompatActivity implements AddTweetDialog.OnFragmentInteractionListener{
    private final int REQUEST_CODE = 20;
    private RecyclerView rvRecyclerView;
    private RecyclerView.LayoutManager lmLayoutManager;
    List<Tweet> tweetsList;
    TweetsAdapter tweetsAdapter;
    User user;
    private Disposable disposable;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets_list);

        Bundle extras = getIntent().getExtras();
        user = (User) extras.get(getResources().getString(R.string.user));

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        rvRecyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        lmLayoutManager = new LinearLayoutManager(this);
        rvRecyclerView.setLayoutManager(lmLayoutManager);
        tweetsAdapter = new TweetsAdapter(this);
        rvRecyclerView.setAdapter(tweetsAdapter);

        String str = sharedPref.getString(getResources().getString(R.string.tweets_list), "");

        Type type = new TypeToken<List<Tweet>>() { }.getType();
        tweetsList = new Gson().fromJson(str, type);

        if(tweetsList==null) tweetsList = new ArrayList<>();

        createObservable();
    }

    private void createObservable() {

        long lastShown = sharedPref.getLong(getResources().getString(R.string.last_shown), 0l);
        Observable<List<Tweet>> listObservable = Observable.fromArray(readTweets(new Date(lastShown)));
        disposable = listObservable.subscribeWith(new DisposableObserver<List<Tweet>>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(List<Tweet> tweets) {
                tweetsList.addAll(tweets);
                Collections.sort(tweetsList, Tweet.datePostedComparator);
                tweetsAdapter.setTweets(tweetsList);

                editor.putLong(getResources().getString(R.string.last_shown), System.currentTimeMillis());
                editor.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    public List<Tweet>  readTweets(Date date) {
        ConditionGroup conditionGroup = ConditionGroup.clause()
                .and(Tweet_Table.datePosted.greaterThan(date));
        return SQLite.select().
                from(Tweet.class).where(conditionGroup).queryList();


    }

    public void addANewTweet(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        AddTweetDialog addTweetDialogFragment = AddTweetDialog.newInstance(user);
        addTweetDialogFragment.show(fm, "fragment_add_tweet");
    }

    public void logoutUser(MenuItem item) {
        editor.putBoolean(getResources().getString(R.string.isLogin), false);
        editor.commit();
        startActivity(new Intent(TweetsListActivity.this, LoginActivity.class)); //Go back to home page
        finish();
    }

    @Override
    public void onFragmentInteraction(Tweet tweet) {
        createObservable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String dataStr = new Gson().toJson(tweetsList);
        editor.putString(getResources().getString(R.string.tweets_list), dataStr);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable!=null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createObservable();
    }

    public void addTestTweets(MenuItem item) {
        Tweet addedTweet = new Tweet("new tweet", 2345, new Date());
        addedTweet.save();
        Tweet addedTweet1 = new Tweet("new tweet1", 2345, new Date());
        addedTweet1.save();
        Tweet addedTweet2 = new Tweet("new tweet2", 2345, new Date());
        addedTweet2.save();
    }
}
