package com.app.blizapp.blizapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.app.blizapp.blizapp.models.Record
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_data_insert.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.widget.AdapterView
import kotlin.collections.ArrayList


class DataInsertActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var dateOfService: Date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_insert)


        val saveDataButton = findViewById<View>(R.id.saveDataBtn) as Button
        saveDataButton.setOnClickListener {
            saveData()
        }

        val logoutButton = findViewById<View>(R.id.logoutBtn) as Button
        logoutButton.setOnClickListener {
            logout()
        }
        dateOfServiceTxt.setOnClickListener{
            prepareDatePickerDialog()
        }
        val spinner = findViewById<View>(R.id.facultySpinner) as Spinner
        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.facultyNames))
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                departmentSpinner.isEnabled = pos == 0
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        val departmentSpinner = findViewById<View>(R.id.departmentSpinner) as Spinner
        val secondAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.engineeringNames))
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = secondAdapter
    }
    private fun showDepartmentDialog(){

    }

    private fun logout() {
        mAuth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }

    private fun saveData() {
        val nameTxt = findViewById<View>(R.id.studentNameTxt) as EditText
        val lastNameTxt = findViewById<View>(R.id.StudentLastNameTxt) as EditText
        val carneTxt = findViewById<View>(R.id.StudentDocumentNumberTxt) as EditText
        val hoursOfServiceTxt = findViewById<View>(R.id.StudentTotalHoursTxt) as EditText
        val spinner = findViewById<View>(R.id.facultySpinner) as Spinner
        val departmentSpinner = findViewById<View>(R.id.departmentSpinner) as Spinner
        val dateOfServiceTxt = findViewById<View>(R.id.dateOfServiceTxt) as EditText


        val nameString = nameTxt.text.toString()
        val lastNameString = lastNameTxt.text.toString()
        val facultyString = spinner.selectedItem.toString()
        var departmentString: String
        val carne = carneTxt.text.toString()
        val carneInt = carne.toInt()

        val hoursOfService = hoursOfServiceTxt.text.toString()
        val hoursOfServiceInt = hoursOfService.toInt()

        if (facultyString != "Ingeniería"){
            departmentString = spinner.selectedItem.toString()
        } else {
            departmentString = departmentSpinner.selectedItem.toString()
        }

        val newRecord = Record(nameString, lastNameString, carneInt, hoursOfServiceInt, facultyString, departmentString ,dateOfService, mAuth.currentUser!!.uid)

        db.collection("records")
                .add(newRecord)
                .addOnSuccessListener {
                    Toast.makeText(this, "Datos del Alumno guardados de forma exitosa", Toast.LENGTH_LONG).show()
                    nameTxt.setText("")
                    lastNameTxt.setText("")
                    carneTxt.setText("")
                    hoursOfServiceTxt.setText("")
                    dateOfServiceTxt.setText("")

                }.addOnFailureListener { exception: Exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
    }

    @SuppressLint("SimpleDateFormat")
    private fun prepareDatePickerDialog(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)
            val sdf = SimpleDateFormat("MM-dd-yyyy")
            var formattedDate = sdf.format(c.time)
            val date = sdf.parse(formattedDate)
            val dateToShow = "$dayOfMonth / ${monthOfYear+1} / $year"
            dateOfServiceTxt.setText(dateToShow)
            this.dateOfService = date

        }, year, month, day)
        dpd.show()
    }


}
