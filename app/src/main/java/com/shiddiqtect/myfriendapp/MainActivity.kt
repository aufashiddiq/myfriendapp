package com.shiddiqtect.myfriendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_friends_fragment.*

class MainActivity : AppCompatActivity() {

    companion object {
        var myFriend: MyFriend? = null

        fun setData(myFriend: MyFriend) {
            this.myFriend = myFriend
        }

        fun removeData() {
            myFriend = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tampilMyFriendsFragment()

    }

    private fun gantiFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment, frameId: Int) {

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)

        transaction.commit()
    }

    fun tampilMyFriendsFragment() {
        gantiFragment(supportFragmentManager,
            MyFriendsFragment.newInstance(),
            R.id.contentFrame)
    }

    fun tampilMyFriendsAddFragment() {
        gantiFragment(supportFragmentManager,
            MyFriendsAddFragment.newInstance(),
            R.id.contentFrame)
    }


}
