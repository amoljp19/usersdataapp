package com.softaai.usersdataapp.usersdata.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.softaai.usersdataapp.R

class NewUserDataActivity : AppCompatActivity() {

    private lateinit var editFirstNameView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_data)
        editFirstNameView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editFirstNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val firstName = editFirstNameView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, firstName)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.softaai.android.userlistsql.REPLY"
    }
}