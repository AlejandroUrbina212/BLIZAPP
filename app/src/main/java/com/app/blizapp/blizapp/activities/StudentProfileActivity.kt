package com.app.blizapp.blizapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.blizapp.blizapp.R

class StudentProfileActivity : AppCompatActivity() {
    companion object {
        private const val ACTIVITY_NUM = 2
        private const val TAG = "StudentProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
    }
}
