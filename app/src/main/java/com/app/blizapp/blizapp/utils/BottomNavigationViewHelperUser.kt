package com.app.blizapp.blizapp.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.activities.DataInsertActivity
import com.app.blizapp.blizapp.activities.UserNewEventActivity
import com.app.blizapp.blizapp.activities.UserEventsActivity
import com.google.firebase.auth.FirebaseAuth
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class BottomNavigationViewHelperUser {
    private val fbAuth = FirebaseAuth.getInstance()
    private val Tag = "BottomNavigationVIewHel"

    fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
        Log.d(this.Tag, "setupBottomNavigationView: Setting up BottomNavigationView")
        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.setTextVisibility(false)
    }

    fun enableNavigation(context: Context, view: BottomNavigationViewEx) {
        view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_my_events -> {
                    val intent1 = Intent(context, UserEventsActivity::class.java)//ACTIVITY_NUM = 0
                    context.startActivity(intent1)
                }
                R.id.ic_new_event -> {
                    val intent2 = Intent(context, UserNewEventActivity::class.java)//ACTIVITY_NUM = 1
                    context.startActivity(intent2)
                }

                R.id.ic_data_insert_original -> {
                    val intent3 = Intent(context, DataInsertActivity::class.java) //ACTIVITY_NUM = 2
                    context.startActivity(intent3)
                }

            }
            false
        }
    }
}