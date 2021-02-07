package com.softaai.usersdataapp.usersdata.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.softaai.usersdataapp.R
import com.softaai.usersdataapp.databinding.UsersDataEditActivityBinding


class NewUserDataActivity : AppCompatActivity() {

    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    lateinit var binding: UsersDataEditActivityBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
                this, R.layout.users_data_edit_activity)

        binding.floatingActionButton.setOnClickListener {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                 requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }

        }

        binding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            val bundle = Bundle()
            if (TextUtils.isEmpty(binding.firstNameEditText.text) || TextUtils.isEmpty(binding.lastNameEditText.text) || TextUtils.isEmpty(binding.emailIdEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                bundle.putString("firstName", binding.firstNameEditText.text.toString())
                bundle.putString("lastName", binding.lastNameEditText.text.toString())
                bundle.putString("email", binding.emailIdEditText.text.toString())

                replyIntent.putExtra(EXTRA_REPLY, bundle)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            binding.imageviewAccountProfile.setImageBitmap(photo)
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.softaai.android.userlistsql.REPLY"
    }
}