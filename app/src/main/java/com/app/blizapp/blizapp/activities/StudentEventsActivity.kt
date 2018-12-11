package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.adapters.EventAdapter
import com.app.blizapp.blizapp.models.Event
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperStudent
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_student_events.*
import kotlinx.android.synthetic.main.activity_user_events.*

class StudentEventsActivity : AppCompatActivity() {
    companion object {
        private const val ACTIVITY_NUM = 1
        private const val TAG = "StudentEventsActivity"
        private val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val mAuth = FirebaseAuth.getInstance()
    }
    private var adapter: EventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_events)

    }
}
