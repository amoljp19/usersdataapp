package com.softaai.usersdataapp.usersdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.model.Data

class UsersListAdapter :
    ListAdapter<Data, UsersListAdapter.UsersDataViewHolder>(UsersDataComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersDataViewHolder {
        return UsersDataViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UsersDataViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindAvtar(current.avatar)
        holder.bindFirstName(current.firstName)
        holder.bindLastName(current.lastName)
        holder.bindEmail(current.email)
    }

    class UsersDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userFirstNameItemView: TextView = itemView.findViewById(R.id.firstNameTextView)
        private val userLastNameItemView: TextView = itemView.findViewById(R.id.lastNameTextView)
        private val userEmailItemView: TextView = itemView.findViewById(R.id.emailTextView)

        fun bindAvtar(imageURL: String?) {
            Glide.with(itemView.context)
                .load(imageURL)
                .centerCrop()
                .into(itemView.findViewById(R.id.imageview_account_profile))
        }

        fun bindFirstName(text: String?) {
            userFirstNameItemView.text = text
        }

        fun bindLastName(text: String?) {
            userLastNameItemView.text = text
        }

        fun bindEmail(text: String?) {
            userEmailItemView.text = text
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