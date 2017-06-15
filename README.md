# twitter_trov

Take-Home assignment

This Project:
  - Uses SQLite Database for storing tweets. (Tweet model is implemented, User model is for getting an idea of how the user object 
  would be.)
  - Used DBFlow ORM.
  - The data, ui, model folders are separate to keep the architecture clean.
  - Used RxJava by creating Obserable and Observer to read and display tweets in a recycler view
  - Use of SharedPrefs to app data
  - Tweets are ordered from latest to oldest.
  - Mostly tested manually except adding of new tweets from another user for which a menu item is hidden in TweetListActivity.
  - Did not test phone rotations. Doing more research on new architecture elements from Google.
  - RecyclerView for displaying tweets.

This Android app lets a user:
  1. Login/Logout:
      - User has been hardcoded to username: trov, password: trov and userId: 1234
      - Use of SharedPrefs to store if the user is logged in.
      - Logout menu provided for a user to logout, which removes the user from sharedprefs.
      - Shows an error if the hardcoded Id and pwd is not used. Saying invalid user.
      
  2. Display a list of tweets:
     - Assumption: A user should be able to see tweets from everyone.
     - A menu item (hidden) is provided on this screen to automatically add 3 new tweets so that when a user comes back he can see
     three new items added to the list.
     - No username is shown with the tweets, but the User model provides the username and in furthur implementation the user name
     can be retrived from the userdata base using the userId in the tweet model so the user can be shown with every tweet.
     
  3. Post a new tweet:
      - Fragment used for letting a user add a new tweet, on saving the tweet the recycler view gets updated with the new tweet. 
      
  4. on launch, if the user is logged in, automatically display older tweets and only query for newer ones
      - Used SharedPrefs as cache for already shown tweets so onResume only new tweets need to be queried. This is achieved by saving 
      last shown tweet date in sharedpref and then using that to query tweets added after that time from the db.
  
  Ideas on testing:
    - Tests for adding new tweets and retriving all tweets
    - Test to query all items from the db.
    - Test to query only new items from the db.
    - For future: Tests for adding/removing new users
    
  If a real webservice was being used:
    - The Observable would handle errors if error is returned.
    - A progress bar can be added since the result is not instant.
    - A sqlite db could be used as a cache to save the already shown items and a call could be made only to retrieve new items.
    
    
    
