package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.app.blizapp.blizapp.R
import android.widget.AdapterView.OnItemClickListener
import com.app.blizapp.blizapp.models.Event
import com.google.firebase.firestore.FirebaseFirestore




class EventDetailsUserActivity : AppCompatActivity() {
    private var assistantStudentsIdsArrayList: ArrayList<String> = ArrayList()
    private var assistantStudentsIdsArray: Array<String> = emptyArray()
    private val selectedItems: ArrayList<String> = ArrayList()
    private var currentEventId: String = ""
    private var currentEventHours: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details_user)


        //Setting Up Toolbar
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Marca los alumnos que asistieron"


        currentEventId = intent.getStringExtra("eventId")
        val studentsListView = findViewById<ListView>(R.id.checkeable_list)
        studentsListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE


        FirebaseFirestore.getInstance().collection("events").document(currentEventId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("ON ERROR", "Listen failed.", e)
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val currentEvent: Event = snapshot.toObject(Event::class.java)!!
                        currentEventHours = currentEvent.getDuration()
                        for (i in currentEvent.getAssistantStudents()) {
                            assistantStudentsIdsArrayList.add(i)
                        }
                        val x = assistantStudentsIdsArrayList.size
                        val array = arrayOfNulls<String>(assistantStudentsIdsArrayList.size)
                        assistantStudentsIdsArray = assistantStudentsIdsArrayList.toArray(array)
                        val y = assistantStudentsIdsArray.size

                        val arrayAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.txt_accepted, assistantStudentsIdsArray)
                        studentsListView.adapter = arrayAdapter

                        studentsListView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                            // selected item

                            val selectedItem = (view as TextView).text.toString()
                            if (selectedItems.contains(selectedItem))
                                selectedItems.remove(selectedItem) //remove deselected item from the list of selected items
                            else
                                selectedItems.add(selectedItem) //add selected item to the list of selected items
                        }
                    } else {
                        Log.d("NULL DATA", "Current data: null")
                    }

                }
        val finishEventBtn = findViewById<Button>(R.id.finishEventBtn)
        finishEventBtn.setOnClickListener {
            val eventsRef = FirebaseFirestore.getInstance().collection("events").document(currentEventId)

            eventsRef
                    .update("state", 2)
                    .addOnSuccessListener {
                        Log.d("hey", "DocumentSnapshot successfully updated!")
                    }
                    .addOnFailureListener { e -> Log.w("hey", "Error updating document", e)
                    }
            finishEvent()
        }
    }

    private fun finishEvent() {
            Toast.makeText(this, "Evento Finalizado Exitosamente", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, UserEventsActivity::class.java))

    }


    override fun onBackPressed() {
        startActivity(Intent(this, UserEventsActivity::class.java))
        finish()
    }

}
