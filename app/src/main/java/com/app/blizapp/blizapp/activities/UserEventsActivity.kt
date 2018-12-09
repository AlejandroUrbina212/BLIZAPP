package com.app.blizapp.blizapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperUser
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class UserEventsActivity : AppCompatActivity() {
    companion object {
        private const val ACTIVITY_NUM = 0
        private const val TAG = "UserEventsActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_events)

        //setupBottonNavView
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        Log.d(TAG, "SetupBottomNavigationView: settinn BottomNavView")
        val bottomNavigationViewEx = findViewById<BottomNavigationViewEx>(R.id.bottomNavViewBar)
        val bottomNavigationViewHelper = BottomNavigationViewHelperUser()
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        bottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }
}
