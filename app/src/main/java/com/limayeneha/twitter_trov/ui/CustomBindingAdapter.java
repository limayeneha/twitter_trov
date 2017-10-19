package com.limayeneha.twitter_trov.ui;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.limayeneha.twitter_trov.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by limayeneha on 10/18/17.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"bind:dateformatted"})
    public static void getDatePostedString(TextView view, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
        view.setText(dateFormat.format(date));

    }

}
