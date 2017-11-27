package com.limayeneha.twitter_trov;

import android.databinding.Observable;

import com.limayeneha.twitter_trov.viewmodel.ItemTweetViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by limayeneha on 10/18/17.
 */

@RunWith(RobolectricGradleTestRunner.class) @Config(constants = BuildConfig.class, sdk = 21)
public class ItemTweetViewModelTest {
    private static final String TWEET_TEXT = "test_tweet";
    private static final Date DATE_POSTED = Calendar.getInstance().getTime();

    private TwitterTrovApplication twitterTrovApplication;

    @Before public void setUpItemPeopleModelTest() {
        twitterTrovApplication = (TwitterTrovApplication) RuntimeEnvironment.application;
    }

    @Test
    public void shouldGetTweetText() throws Exception {
        Tweet tweet = new Tweet();
        tweet.tweetText = TWEET_TEXT;
        ItemTweetViewModel itemTweetViewModel = new ItemTweetViewModel(tweet);
        assertEquals(tweet.tweetText, itemTweetViewModel.getTweetText());
    }

    @Test
    public void shouldGetTweetDate() throws Exception {
        Tweet tweet = new Tweet();
        tweet.datePosted = Calendar.getInstance().getTime();
        ItemTweetViewModel itemTweetViewModel = new ItemTweetViewModel(tweet);
        assertEquals(tweet.datePosted, itemTweetViewModel.getDatePosted());
    }

    @Test public void shouldNotifyWhenSetTweet() throws Exception {
        Tweet tweet = new Tweet();
        ItemTweetViewModel itemPeopleViewModel = new ItemTweetViewModel(tweet);
        Observable.OnPropertyChangedCallback mockCallback =
                mock(Observable.OnPropertyChangedCallback.class);
        itemPeopleViewModel.addOnPropertyChangedCallback(mockCallback);
        itemPeopleViewModel.setTweet(tweet);
        verify(mockCallback).onPropertyChanged(any(Observable.class), anyInt());
    }
}
