package com.app.blizapp.blizapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperUser
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class UserNewEventActivity : AppCompatActivity() {
    companion object {
        private const val ACTIVITY_NUM = 1
        private const val TAG = "UserNewEventActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_new_event)

        setupBottomNavigationView()
        //setupBottonNavView

    }
    private fun setupBottomNavigationView(){
        Log.d(UserNewEventActivity.TAG, "SetupBottomNavigationView: settinn BottomNavView")
        val bottomNavigationViewEx = findViewById<BottomNavigationViewEx>(R.id.bottomNavViewBar)
        val bottomNavigationViewHelper = BottomNavigationViewHelperUser()
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        bottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(UserNewEventActivity.ACTIVITY_NUM)
        menuItem.isChecked = true
    }
}
