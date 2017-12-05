package com.limayeneha.twitter_trov.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView

import com.limayeneha.twitter_trov.R

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by limayeneha on 10/18/17.
 */

object CustomBindingAdapter {

  @BindingAdapter("bind:dateformatted")
  fun getDatePostedString(view: TextView, date: Date) {
    val dateFormat = SimpleDateFormat("MM/dd HH:mm")
    view.text = dateFormat.format(date)

  }

}
