package com.limayeneha.twitter_trov.model

import com.limayeneha.twitter_trov.data.TweetsDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

import java.util.Comparator
import java.util.Date

/**
 * Created by limayeneha on 5/27/17.
 */
@Table(database = TweetsDatabase::class)
class Tweet : BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    var id: Int = 0

    @Column
    var userId: Int = 0


    @Column
    var tweetText: String = ""

    @Column
    var datePosted: Date? = null

    constructor() {

    }

    constructor(tweetText: String, userId: Int, datePosted: Date) {
        this.tweetText = tweetText
        this.userId = userId
        this.datePosted = datePosted
    }

    companion object {

        var datePostedComparator: Comparator<Tweet> = Comparator { p1, p2 -> p2.datePosted!!.compareTo(p1.datePosted) }
    }
}
