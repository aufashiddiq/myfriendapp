package com.shiddiqtect.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_fragment.*

class MyFriendsFragment : Fragment() {

    private var listTeman: MutableList<MyFriend>? = null

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDAO? = null

    companion object {
        fun newInstance() : MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDb()
        initView()
    }

    private fun initLocalDb() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }

//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman () {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer { r ->

            listTeman = r as MutableList<MyFriend>?

            when {
                listTeman?.size == 0 -> tampilToast("Belum ada teman")

                else -> {
                    tampilTeman()
                }
            }
        })
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

//    private fun simulasiDataTeman() {
//        listTeman = ArrayList()
//
//        listTeman.add(MyFriend("Aufa", "Laki-laki",
//            "aufa@gmail.com", "08123456789", "Malang"))
//        listTeman.add(
//            MyFriend("Bambang", "Laki-laki",
//            "bambang@gmail.com", "0812318237198", "Tumpang")
//        )
//    }

    private fun tampilTeman() {
        listMyFriend.layoutManager = LinearLayoutManager(activity)
        listMyFriend.adapter = MyFriendAdapter(activity!!,
            listTeman as ArrayList<MyFriend>)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }


}