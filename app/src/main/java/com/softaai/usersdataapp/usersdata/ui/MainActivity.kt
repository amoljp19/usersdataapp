package com.softaai.usersdataapp.usersdata.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.usersdata.adapter.UsersListAdapter
import com.softaai.usersdataapp.usersdata.viewmodel.UsersDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val usersDataViewModel : UsersDataViewModel by viewModels()

    private val newUserDataActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UsersListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        usersDataViewModel.fetchUsersDataList()

        usersDataViewModel.UsersDataListLiveData.observe(this, Observer { users ->
            users?.let { adapter.submitList(it) }
        })



        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewUserDataActivity::class.java)
            startActivityForResult(intent, newUserDataActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newUserDataActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.extras?.let {

                val user = Data(it.getString("img", ""), it.getString("email", ""), it.getString("firstName", ""), 13, it.getString("lastName", ""))
                usersDataViewModel.insert(user)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

}