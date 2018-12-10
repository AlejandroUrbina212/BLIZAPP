package com.app.blizapp.blizapp.activities
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperUser
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import android.app.TimePickerDialog
import android.view.View
import android.widget.*
import com.app.blizapp.blizapp.models.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_new_event.*
import java.text.SimpleDateFormat
import java.util.*


class UserNewEventActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ACTIVITY_NUM = 1
        private const val TAG = "UserNewEventActivity"
        private var initialHour: Int = 0
        private var endHour: Int = 0
        private var initialHourString = ""
        private var endHourString = ""
        private var newEventDate: Date = Calendar.getInstance().time
        private var targetYearInt: Int = 0
        private var dateSelected: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_new_event)

        //Setting Up Toolbar
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Nuevo Evento"

        setupBottomNavigationView()
        //setupBottonNavView

        //for the timepickers
        val newEventInitialHourEditText = findViewById<EditText>(R.id.newEventInitialHourTxt)
        val newEventEndHourEditText = findViewById<EditText>(R.id.newEventEndHourTxt)
        val newEventDurationEditText = findViewById<EditText>(R.id.newEventDurationTxt)

        val newEventDateButton = findViewById<Button>(R.id.newEventDateBtn)
        newEventDateButton.setOnClickListener {
            prepareDatePickerDialog()
            dateSelected = true
        }

        newEventInitialHourEditText.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this@UserNewEventActivity, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                val amPm: String = if (hourOfDay >= 12) {
                    "PM"
                } else {
                    "AM"
                }
                newEventInitialHourEditText.setText(String.format("%02d:%02d", hourOfDay, minutes) + " horas")
                initialHour = hourOfDay
                initialHourString = String.format("%02d:%02d", hourOfDay, minutes)

            }, 0, 0, false)
            timePickerDialog.show()
        }

        newEventEndHourEditText.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this@UserNewEventActivity, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                newEventEndHourEditText.setText(String.format("%02d:%02d", hourOfDay, minutes) + " horas")
                endHour = hourOfDay
                endHourString = String.format("%02d:%02d", hourOfDay, minutes)
                if ((endHour - initialHour) < 0) {
                    Toast.makeText(this, "La hora de finalización debe ser después de la de inicio", Toast.LENGTH_SHORT).show()
                    newEventEndHourEditText.setText("")
                } else {
                    newEventDurationEditText.setText("Duración del evento: ${endHour - initialHour} horas")
                }

            }, 0, 0, false)
            timePickerDialog.show()
        }

        val targetYearSpinner = findViewById<View>(R.id.targetYearSpinner) as Spinner
        val targetYearAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.targetYear))
        targetYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        targetYearSpinner.adapter = targetYearAdapter
        targetYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                targetYearInt = position + 1
            }
        }

        val facultySpinner = findViewById<View>(R.id.facultySpinner) as Spinner
        val facultyAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.facultyNames))
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        facultySpinner.adapter = facultyAdapter


        val saveEventButton = findViewById<Button>(R.id.saveEventButton)
        saveEventButton.setOnClickListener {
            saveEvent()
        }
    }

    private fun saveEvent() {
        val newEventNameEditText = findViewById<EditText>(R.id.newEventNameTxt)
        val newEventDescriptionEditText = findViewById<EditText>(R.id.newEventDescriptionTxt)
        val newEventPlaceEditText = findViewById<EditText>(R.id.newEventPlaceTxt)
        val newEventCapacityEditText = findViewById<EditText>(R.id.newEventCapacityTxt)
        val newEventInitialHourEditText = findViewById<EditText>(R.id.newEventInitialHourTxt)
        val newEventEndHourEditText = findViewById<EditText>(R.id.newEventEndHourTxt)

        val eventNameString = newEventNameEditText.text.toString()
        val eventDescriptionString = newEventDescriptionEditText.text.toString()
        val eventPlaceString = newEventPlaceEditText.text.toString()
        val eventCapacityString = newEventCapacityEditText.text.toString()
        val eventInitialHourString = newEventInitialHourEditText.text.toString()
        val eventEndHourString = newEventEndHourEditText.text.toString()
        //for the date ill use the variable newEventDate


        if (!eventNameString.isEmpty() && !eventDescriptionString.isEmpty() && !eventPlaceString.isEmpty() && !eventCapacityString.isEmpty()
                && !eventInitialHourString.isEmpty() && !eventEndHourString.isEmpty() && dateSelected) {

            val completeInitialHourString = "${newEventDateBtn.text} $initialHourString"
            val completeEndHourString = "${newEventDateBtn.text} $initialHourString"

            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            //final crap to Firebase
            val completeInitialHourDate = sdf.parse(completeInitialHourString)
            val completeEndHourDate = sdf.parse(completeEndHourString)

            val eventDurationInt = endHour - initialHour
            val eventFacultyName = facultySpinner.selectedItem.toString()


            val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
            val eventsIdRef = FirebaseFirestore.getInstance().collection("events").document()
            val newEventId = eventsIdRef.id
            val newEvent = Event(
                    eventId = newEventId,
                    userId = currentUserId,
                    eventName = eventNameString,
                    eventDescription = eventDescriptionString,
                    place = eventPlaceString,
                    date = newEventDate,
                    startHour = completeInitialHourDate,
                    endHour = completeEndHourDate,
                    duration = eventDurationInt,
                    targetYear = targetYearInt,
                    targetCareer = eventFacultyName,
                    capacity = eventCapacityString.toInt(),
                    state = 0
            )
            db.collection("events")
                    .document(newEventId)
                    .set(newEvent)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Evento Guardado Exitosamente", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { exception: java.lang.Exception ->
                        Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                    }

        } else {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_LONG).show()
        }

    }


    private fun setupBottomNavigationView() {
        Log.d(UserNewEventActivity.TAG, "SetupBottomNavigationView: settinn BottomNavView")
        val bottomNavigationViewEx = findViewById<BottomNavigationViewEx>(R.id.bottomNavViewBar)
        val bottomNavigationViewHelper = BottomNavigationViewHelperUser()
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        bottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(UserNewEventActivity.ACTIVITY_NUM)
        menuItem.isChecked = true
    }

    @SuppressLint("SimpleDateFormat")
    private fun prepareDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)
            val sdf = SimpleDateFormat("MM-dd-yyyy")
            val formattedDate = sdf.format(c.time)
            val date = sdf.parse(formattedDate)
            val dateToShow = "$dayOfMonth-${monthOfYear + 1}-$year"
            newEventDateBtn.text = dateToShow
            newEventDate = date
        }, year, month, day)
        dpd.show()
    }
}
