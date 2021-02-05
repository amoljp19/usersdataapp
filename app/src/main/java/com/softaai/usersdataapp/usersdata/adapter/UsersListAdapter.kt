package com.softaai.usersdataapp.usersdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.model.Data

class UsersListAdapter : ListAdapter<Data, UsersListAdapter.UsersDataViewHolder>(UsersDataComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersDataViewHolder {
        return UsersDataViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UsersDataViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.firstName)
    }

    class UsersDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): UsersDataViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_item, parent, false)
                return UsersDataViewHolder(view)
            }
        }
    }

    class UsersDataComparator : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.firstName == newItem.firstName
        }
    }
}