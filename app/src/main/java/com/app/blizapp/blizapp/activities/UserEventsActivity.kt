package com.app.blizapp.blizapp.activities
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.adapters.EventAdapter
import com.app.blizapp.blizapp.models.Event
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperUser
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_user_events.*

class UserEventsActivity : AppCompatActivity() {
    companion object {
        private const val ACTIVITY_NUM = 0
        private const val TAG = "UserEventsActivity"
        private val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val mAuth = FirebaseAuth.getInstance()
    }


    private var adapter: EventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_events)
        createEventRequestListener()

        //Setting Up Toolbar
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Mis Eventos"

        //setupBottonNavView
        setupBottomNavigationView()

    }

    private fun createEventRequestListener() {
        events_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("events").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("userId", uid)

        val options = FirestoreRecyclerOptions.Builder<Event>().setQuery(query,Event::class.java).build()

        //val options = FirestoreRecyclerOptions.Builder<Event>().setQuery(query, Event::class.java).build()

        adapter = EventAdapter(options) { request ->
            val eventsRef = FirebaseFirestore.getInstance().collection("events")

            val query = eventsRef
                    .whereEqualTo("eventId", request.getEventId())

            query.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Inside onComplete function!")
                    for (document in task.result) {
                        val idCurrentEvent = document.id
                        val intent = Intent(this, EventResumeActivity::class.java)
                        intent.putExtra("eventId", idCurrentEvent)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        events_recycler_view.adapter = adapter
        adapter!!.startListening()
    }
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.nav_logOut -> {
                mAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return true
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

    override fun onBackPressed() {
        moveTaskToBack(true)
    }


}
