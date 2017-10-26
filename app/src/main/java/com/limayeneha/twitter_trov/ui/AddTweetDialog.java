package com.limayeneha.twitter_trov.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.databinding.FragmentAddTweetDialogBinding;
import com.limayeneha.twitter_trov.model.Tweet;
import com.limayeneha.twitter_trov.model.User;

import java.util.Date;

/**
 * Created by limayeneha on 5/27/17.
 */

public class AddTweetDialog extends DialogFragment {

    private static final String USER_ID = "userID";
    private static final String USER_NAME = "user_name";

    private String tweetText;
    int userId;
    String username;
    EditText ettweetText;

    private OnFragmentInteractionListener mListener;


    private boolean handleMenuClick(MenuItem item) {
        if (item.getItemId() == R.id.save_added) {
            tweetText = ettweetText.getText().toString();
            if (tweetText.length() < 1) {
                Toast.makeText(AddTweetDialog.this.getActivity(), "Enter missing information.", Toast.LENGTH_SHORT).show();
            } else {
                Tweet addedTweet = new Tweet(tweetText, userId, new Date());
                addedTweet.save();

                OnFragmentInteractionListener listener = (OnFragmentInteractionListener) AddTweetDialog.this.getActivity();
                listener.onFragmentInteraction(addedTweet);


                AddTweetDialog.this.dismiss();
                return true;
            }
        } else {
            AddTweetDialog.this.dismiss();
            return true;
        }

        return false;
    }


    public static AddTweetDialog newInstance(User user) {
        AddTweetDialog fragment = new AddTweetDialog();
        Bundle args = new Bundle();
        args.putInt(USER_ID, user.id);
        args.putString(USER_NAME, user.username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            userId = getArguments().getInt(USER_ID);
            username = getArguments().getString(USER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentAddTweetDialogBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_tweet_dialog, container, false);
        binding.myToolbar.inflateMenu(R.menu.add_dialog_toolbar);
        binding.myToolbar.setTitle("ADD A NEW TWEET");
        binding.myToolbar.setOnMenuItemClickListener(item -> handleMenuClick(item));
        ettweetText = binding.etAddTweetFrag;

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Tweet tweet);
    }


}
