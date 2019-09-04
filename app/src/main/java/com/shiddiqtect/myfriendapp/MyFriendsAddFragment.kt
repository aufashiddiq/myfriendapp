package com.shiddiqtect.myfriendapp

import android.os.Bundle
import android.os.Message
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.my_friends_add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment() {

    companion object {

        fun newInstance(): MyFriendsAddFragment {
            return MyFriendsAddFragment()
        }
    }

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDAO? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
        checkData()
    }

    private fun initView() {
        btnSave.setOnClickListener {
            validasiInput()
        }
        btnDelete.setOnClickListener {
            if (MainActivity.myFriend != null) {
                hapusTeman(MainActivity.myFriend!!)
                MainActivity.removeData()
            }
        }
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun checkData() {

        val myFriendData = MainActivity.myFriend
        if (myFriendData != null) {
            edtName.setText(myFriendData.nama)
            edtAddress.setText(myFriendData.alamat)
            edtTelp.setText(myFriendData.telp)
            edtEmail.setText(myFriendData.email)
            if (myFriendData.kelamin == "Laki-laki")
                spinnerGender.setSelection(1)
            else if (myFriendData.kelamin == "Perempuan")
                spinnerGender.setSelection(2)
            else
                spinnerGender.setSelection(0)
        }
    }

    private fun validasiInput() {

        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when {

            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih kelamin") -> tampilToast("Pilih jenis kelamin!")
            emailInput.isEmpty() -> edtEmail.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"

            else -> {

                if (MainActivity.myFriend != null) {
                    val teman = MyFriend(MainActivity.myFriend?.temanId, namaInput, genderInput,
                        emailInput, telpInput, alamatInput)
                    updateTeman(teman)
                }
                else {
                    val teman = MyFriend(null, namaInput, genderInput,
                        emailInput, telpInput, alamatInput)
                    tambahDataTeman(teman)
                }
            }
        }

    }

    private fun updateTeman(teman: MyFriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.updateTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
            MainActivity.removeData()
        }
    }

    private fun hapusTeman(teman: MyFriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.deleteTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
            MainActivity.removeData()
        }
    }

    private fun tambahDataTeman(teman: MyFriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}