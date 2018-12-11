package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class EventResumeActivity : AppCompatActivity() {
    private val sdf = SimpleDateFormat("MM/dd/yyyy")
    private val shf = SimpleDateFormat("hh:mm a")
    private var arrayOfStudents = ArrayList<String>()
    private var currentEventId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_resume)

        //Setting Up Toolbar
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Resumen del Evento"

        //bind textViews with view
        val eventNameEditText = findViewById<EditText>(R.id.eventNameTxt)
        val eventDescriptionEditText = findViewById<EditText>(R.id.eventDescriptionTxt)
        val eventPlaceEditText = findViewById<EditText>(R.id.eventPlaceTxt)
        val eventDateEditText = findViewById<EditText>(R.id.eventDateTxt)
        val eventTimeEditText = findViewById<EditText>(R.id.eventTimeTxt)

        val eventAssistantStudentsEditText = findViewById<EditText>(R.id.eventAssistantStudentsTxt)
        currentEventId = intent.getStringExtra("eventId")

        FirebaseFirestore.getInstance().collection("events").document(currentEventId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("ON ERROR", "Listen failed.", e)
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val currentEvent: Event = snapshot.toObject(Event::class.java)!!
                        Log.d("SNAPSHOT WAS", "Current data: " + snapshot.data)
                        //sending info from the eventObject to the EditTexts
                        eventNameEditText.setText(currentEvent.getEventName())
                        eventDescriptionEditText.setText(currentEvent.getEventDescription())
                        eventPlaceEditText.setText(currentEvent.getPlace())
                        val eventDate = currentEvent.getDate()

                        val eventDateString = sdf.format(eventDate)

                        val eventStartHour = currentEvent.getStartHour()
                        val eventStartHourString = shf.format(eventStartHour)
                        val eventEndHour = currentEvent.getEndHour()
                        val eventEndHourString = shf.format(eventEndHour)

                        val eventDateAndTimeString = "$eventDateString"

                        eventDateEditText.setText(eventDateAndTimeString)
                        eventTimeEditText.setText("Desde: $eventStartHourString hasta: $eventEndHourString")
                        arrayOfStudents = currentEvent.getAssistantStudents()
                        eventAssistantStudentsEditText.setText("${arrayOfStudents.size}")
                    } else {
                        Log.d("NULL DATA", "Current data: null");
                    }

                }
        val redirectToListButton = findViewById<Button>(R.id.redirectToListBtn)
        redirectToListButton.setOnClickListener {
            redirectToEventDetailActivity()
        }

    }

    private fun redirectToEventDetailActivity() {
        val intent = Intent(this, EventDetailsUserActivity::class.java)
        intent.putExtra("eventId", currentEventId)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, UserEventsActivity::class.java))
        finish()
    }
}
