package com.softaai.usersdataapp.usersdata.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.model.Data

class Items_RVAdapter(private var itemsCells: ArrayList<Data?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mcontext: Context

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<Data?>) {
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Data {
        return itemsCells[position]!!
    }

    fun addLoadingView() {
        Handler().post {
            itemsCells.add(null)
            notifyItemInserted(itemsCells.size - 1)
        }
    }

    fun removeLoadingView() {
        if (itemsCells.size != 0) {
            itemsCells.removeAt(itemsCells.size - 1)
            notifyItemRemoved(itemsCells.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(mcontext).inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsCells[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val userFirstNameItemView: TextView =
                holder.itemView.findViewById(R.id.firstNameTextView)
            val userLastNameItemView: TextView = holder.itemView.findViewById(R.id.lastNameTextView)
            val userEmailItemView: TextView = holder.itemView.findViewById(R.id.emailTextView)

            Glide.with(holder.itemView.context)
                .load(itemsCells[position]!!.avatar)
                .centerCrop()
                .into(holder.itemView.findViewById(R.id.imageview_account_profile))

            userFirstNameItemView.text = itemsCells[position]!!.firstName
            userLastNameItemView.text = itemsCells[position]!!.lastName
            userEmailItemView.text = itemsCells[position]!!.email
        }
    }
}
