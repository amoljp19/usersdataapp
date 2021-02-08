package com.softaai.usersdataapp.usersdata.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.usersdata.adapter.Items_RVAdapter
import com.softaai.usersdataapp.usersdata.adapter.OnLoadMoreListener
import com.softaai.usersdataapp.usersdata.adapter.RecyclerViewLoadMoreScroll
import com.softaai.usersdataapp.usersdata.viewmodel.UsersDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val usersDataViewModel: UsersDataViewModel by viewModels()

    private val newUserDataActivityRequestCode = 1

    lateinit var loadMoreItemsCells: ArrayList<Data?>
    lateinit var adapter: Items_RVAdapter
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)


        setRVLayoutManager()

        setRVScrollListener()

        usersDataViewModel.fetchUsersDataList()
        usersDataViewModel.UsersDataListLiveData.observe(this, Observer { users ->
            users?.let {
                loadMoreItemsCells = it as ArrayList<Data?>
                adapter = Items_RVAdapter(loadMoreItemsCells)
                recyclerView.adapter = adapter
                adapter.addData(loadMoreItemsCells)
            }
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewUserDataActivity::class.java)
            startActivityForResult(intent, newUserDataActivityRequestCode)
        }
    }

    private fun LoadMoreData() {

        adapter.addLoadingView()

        //loadMoreItemsCells = ArrayList<Data?>()

        val start = adapter.itemCount

        val end = start + 5

        Handler().postDelayed({
            usersDataViewModel.loadMoreUserData()
            adapter.removeLoadingView()
            adapter.addData(loadMoreItemsCells)
            scrollListener.setLoaded()
            findViewById<RecyclerView>(R.id.recyclerview).post {
                adapter.notifyDataSetChanged()
            }
        }, 3000)

    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recyclerview).layoutManager = mLayoutManager
        findViewById<RecyclerView>(R.id.recyclerview).setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(this)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        findViewById<RecyclerView>(R.id.recyclerview).addOnScrollListener(scrollListener)
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