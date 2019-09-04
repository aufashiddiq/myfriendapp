package com.shiddiqtect.myfriendapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyFriendDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriend)

    @Query("SELECT * FROM MyFriend")
    fun ambilSemuaTeman(): LiveData<List<MyFriend>>

    @Update
    fun updateTeman(friend: MyFriend)

    @Delete
    fun deleteTeman(friend: MyFriend)
}