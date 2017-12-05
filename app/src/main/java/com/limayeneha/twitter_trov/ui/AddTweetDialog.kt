package com.limayeneha.twitter_trov.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast

import com.limayeneha.twitter_trov.R
import com.limayeneha.twitter_trov.databinding.FragmentAddTweetDialogBinding
import com.limayeneha.twitter_trov.model.Tweet
import com.limayeneha.twitter_trov.model.User

import java.util.Date

/**
 * Created by limayeneha on 5/27/17.
 */

class AddTweetDialog : DialogFragment() {

  private lateinit var tweetText: String
  internal var userId: Int = 0
  internal lateinit var username: String
  internal lateinit var ettweetText: EditText

  private fun handleMenuClick(item: MenuItem): Boolean {
    if (item.itemId == R.id.save_added) {
      tweetText = ettweetText.text.toString()
      if (tweetText.length < 1) {
        Toast.makeText(this@AddTweetDialog.activity, "Enter missing information.", Toast.LENGTH_SHORT).show()
      } else {
        val addedTweet = Tweet(tweetText, userId, Date())
        addedTweet.save()

        val listener = this@AddTweetDialog.activity as OnFragmentInteractionListener
        listener.onFragmentInteraction(addedTweet)


        this@AddTweetDialog.dismiss()
        return true
      }
    } else {
      this@AddTweetDialog.dismiss()
      return true
    }

    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)

    if (arguments != null) {
      userId = arguments.getInt(USER_ID)
      username = arguments.getString(USER_NAME)
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = DataBindingUtil.inflate<FragmentAddTweetDialogBinding>(
        inflater!!, R.layout.fragment_add_tweet_dialog, container, false)
    binding.myToolbar.inflateMenu(R.menu.add_dialog_toolbar)
    binding.myToolbar.title = "ADD A NEW TWEET"
    binding.myToolbar.setOnMenuItemClickListener { item -> handleMenuClick(item) }
    ettweetText = binding.etAddTweetFrag

    return binding.root
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    dialog.window.requestFeature(Window.FEATURE_NO_TITLE)

    dialog.window.setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
  }

  interface OnFragmentInteractionListener {
    fun onFragmentInteraction(tweet: Tweet)
  }

  companion object {

    private val USER_ID = "userID"
    private val USER_NAME = "user_name"


    fun newInstance(user: User): AddTweetDialog {
      val fragment = AddTweetDialog()
      val args = Bundle()
      args.putInt(USER_ID, user.id)
      args.putString(USER_NAME, user.username)
      fragment.arguments = args
      return fragment
    }
  }


}
