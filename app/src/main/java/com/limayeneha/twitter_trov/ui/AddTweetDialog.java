package com.limayeneha.twitter_trov.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.limayeneha.twitter_trov.R;
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

    EditText etAddTweetFrag;

    private OnFragmentInteractionListener mListener;

    public AddTweetDialog() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tweet_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.save_added) {
                    if(etAddTweetFrag!=null)
                        tweetText = etAddTweetFrag.getText().toString();

                    if(etAddTweetFrag.length()<1 ) {
                        Toast.makeText(getActivity(), "Enter missing information.", Toast.LENGTH_SHORT).show();
                    } else {
                        Tweet addedTweet = new Tweet(tweetText, userId, new Date());
                        addedTweet.save();

                        OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                        listener.onFragmentInteraction(addedTweet);


                        dismiss();
                        return true;
                    }
                } else {
                    dismiss();
                    return true;
                }

                return false;
            }
        });
        toolbar.inflateMenu(R.menu.add_dialog_toolbar);
        toolbar.setTitle("ADD A NEW TWEET");


        etAddTweetFrag = (EditText) view.findViewById(R.id.etAddTweetFrag);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Tweet tweet);
    }



}
