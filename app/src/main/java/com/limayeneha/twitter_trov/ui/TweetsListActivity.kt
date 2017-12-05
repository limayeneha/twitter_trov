package com.limayeneha.twitter_trov.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.preference.PreferenceManager
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.support.v7.widget.RecyclerView

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.limayeneha.twitter_trov.R
import com.limayeneha.twitter_trov.databinding.ActivityTweetsListBinding
import com.limayeneha.twitter_trov.model.Tweet
import com.limayeneha.twitter_trov.model.Tweet_Table
import com.limayeneha.twitter_trov.model.User
import com.raizlabs.android.dbflow.sql.language.ConditionGroup
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.provider.ContentUtils.queryList

import java.lang.reflect.Type
import java.util.ArrayList
import java.util.Collections
import java.util.Date

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class TweetsListActivity : AppCompatActivity(), AddTweetDialog.OnFragmentInteractionListener {
    private val REQUEST_CODE = 20
    private lateinit var rvRecyclerView: RecyclerView
    private lateinit var lmLayoutManager: RecyclerView.LayoutManager
    internal var tweetsList: MutableList<Tweet>? = null
    internal lateinit var tweetsAdapter: TweetsAdapter
    internal lateinit var user: User
    private lateinit var disposable: Disposable
    internal lateinit var sharedPref: SharedPreferences
    internal lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTweetsListBinding>(this, R.layout.activity_tweets_list)

        val extras = intent.extras
        user = extras!!.get(EXTRA_USER) as User

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPref.edit()

        rvRecyclerView = binding.rvRecyclerView
        lmLayoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        rvRecyclerView.layoutManager = lmLayoutManager
        tweetsAdapter = TweetsAdapter()
        rvRecyclerView.adapter = tweetsAdapter

        val str = sharedPref.getString(resources.getString(R.string.tweets_list), "")

        val type = object : TypeToken<List<Tweet>>() {

        }.type
        tweetsList = Gson().fromJson<List<Tweet>>(str, type) as MutableList<Tweet>?

        tweetsList = ArrayList()

        createObservable()
    }

    private fun createObservable() {

        val lastShown = sharedPref.getLong(resources.getString(R.string.last_shown), 0L)

        disposable = Observable.fromCallable { readTweets(Date(lastShown)) }
                .subscribeOn(Schedulers.io())  // Do logic in a separate thread
                .observeOn(AndroidSchedulers.mainThread()) // Once it's finished, post result in main thread
                .doOnNext { tweets ->
                    tweetsList!!.addAll(tweets)
                    Collections.sort(tweetsList!!, Tweet.datePostedComparator)
                    tweetsAdapter.setTweets(tweetsList!!)

                    editor.putLong(resources.getString(R.string.last_shown), System.currentTimeMillis())
                    editor.commit()
                }
                .subscribe()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        return true
    }

    fun readTweets(date: Date): List<Tweet> {
        val conditionGroup = ConditionGroup.clause()
                .and(Tweet_Table.datePosted.greaterThan(date))
        return SQLite.select().from(Tweet::class.java).where(conditionGroup).queryList()

    }

    fun addANewTweet(item: MenuItem) {
        val fm = supportFragmentManager
        val addTweetDialogFragment = AddTweetDialog.newInstance(user)
        addTweetDialogFragment.show(fm, "fragment_add_tweet")
    }

    fun logoutUser(item: MenuItem) {
        editor.putBoolean(resources.getString(R.string.isLogin), false)
        editor.commit()
        startActivity(Intent(this@TweetsListActivity, LoginActivity::class.java)) //Go back to home page
        finish()
    }

    override fun onFragmentInteraction(tweet: Tweet) {
        createObservable()
    }

    override fun onPause() {
        super.onPause()
        val dataStr = Gson().toJson(tweetsList)
        editor.putString(resources.getString(R.string.tweets_list), dataStr)
        editor.commit()
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onResume() {
        super.onResume()
        createObservable()
    }

    companion object {
        private val EXTRA_USER = "EXTRA_USER"

        fun launchTweetsList(context: Context, user: User): Intent {
            val intent = Intent(context, TweetsListActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            return intent
        }
    }
    //
    //    public void addTestTweets(MenuItem item) {
    //        Tweet addedTweet = new Tweet("new tweet", 2345, new Date());
    //        addedTweet.save();
    //        Tweet addedTweet1 = new Tweet("new tweet1", 2345, new Date());
    //        addedTweet1.save();
    //        Tweet addedTweet2 = new Tweet("new tweet2", 2345, new Date());
    //        addedTweet2.save();
    //    }
}
