package com.app.blizapp.blizapp.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.utils.BottomNavigationViewHelperUser
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlin.collections.ArrayList


class DataInsertActivity : AppCompatActivity() {
    //TODO: Hay que poner una forma de que se busque el carné y se llenen los demás campos.

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var dateOfService: Date = Calendar.getInstance().time

    companion object {
        private const val ACTIVITY_NUM = 2
        private const val TAG = "DataInsertActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_insert)

        //Setting Up Toolbar
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Ingreso de Datos Individual"

        //setupBottonNavView
        setupBottomNavigationView()

        val saveDataButton = findViewById<View>(R.id.saveDataBtn) as Button
        saveDataButton.setOnClickListener {
            saveData()
        }

        dateOfServiceBtn.setOnClickListener {
            prepareDatePickerDialog()
        }
        val spinner = findViewById<View>(R.id.facultySpinner) as Spinner
        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.facultyNames))
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter


        val departmentSpinner = findViewById<View>(R.id.departmentSpinner) as Spinner
        var list: ArrayList<String>
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                list = generateArrayByDepartment(pos)
                setDepartmentSpinner(list, departmentSpinner)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }

    fun setDepartmentSpinner(list: ArrayList<String>, departmentSpinner: Spinner) {
        val secondAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                list)

        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = secondAdapter
    }


    private fun saveData() {
        val nameTxt = findViewById<View>(R.id.studentNameTxt) as EditText
        val lastNameTxt = findViewById<View>(R.id.StudentLastNameTxt) as EditText
        val carneTxt = findViewById<View>(R.id.StudentDocumentNumberTxt) as EditText
        val hoursOfServiceTxt = findViewById<View>(R.id.StudentTotalHoursTxt) as EditText
        val spinner = findViewById<View>(R.id.facultySpinner) as Spinner
        val departmentSpinner = findViewById<View>(R.id.departmentSpinner) as Spinner
        val dateOfServiceBtn = findViewById<View>(R.id.dateOfServiceBtn) as Button



        val nameString = nameTxt.text.toString()
        val lastNameString = lastNameTxt.text.toString()
        val facultyString = spinner.selectedItem.toString()
        val departmentString: String
        val carne = carneTxt.text.toString()


        val hoursOfService = hoursOfServiceTxt.text.toString()


        departmentString = if (facultyString == "Vida Estudiantil" || facultyString == "Relaciones Públicas" ||
                facultyString == "SEEA") {
            spinner.selectedItem.toString()
        } else {
            departmentSpinner.selectedItem.toString()
        }

        if (nameString != "" && lastNameString != "" && carne != "" && hoursOfService != ""){
            val carneInt = carne.toInt()
            val hoursOfServiceInt = hoursOfService.toInt()
            val newRecord = Record(nameString, lastNameString, carneInt, hoursOfServiceInt, facultyString,
                    departmentString, dateOfService, mAuth.currentUser!!.uid, Calendar.getInstance().time)

            db.collection("records")
                    .add(newRecord)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Datos del Alumno guardados de forma exitosa", Toast.LENGTH_LONG).show()
                        nameTxt.setText("")
                        lastNameTxt.setText("")
                        carneTxt.setText("")
                        hoursOfServiceTxt.setText("")
                        dateOfServiceBtn.text = "CLICK PARA INGRESAR LA FECHA DE REALIZACIÓN"

                    }.addOnFailureListener { exception: Exception ->
                        Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                    }
        } else {
            Toast.makeText(this, "Por favor llene todos los campos con datos válidos", Toast.LENGTH_SHORT).show()
        }

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
            val dateToShow = "$dayOfMonth / ${monthOfYear + 1} / $year"
            dateOfServiceBtn.text = dateToShow
            this.dateOfService = date

        }, year, month, day)
        dpd.show()
    }

    private fun generateArrayByDepartment(indexOfFacultySpinner: Int): ArrayList<String> {
        var arrayListByDepartment = ArrayList<String>()
        when (indexOfFacultySpinner) {
            0 -> {
                arrayListByDepartment = arrayListOf("Vida Estudiantil")
            }
            1 -> {
                arrayListByDepartment = arrayListOf("Relaciones Públicas")
            }
            2 -> {
                arrayListByDepartment = arrayListOf("SEEA")
            }
            3 -> {
                arrayListByDepartment = arrayListOf("Administración"
                        , "Bioinformática", "Biomédica", "Biotec. Industrial", "Ciencias de Alimentos"
                        , "Civil", "Civil Ambiental", "Civil Arquitectónica", "Civil Industrial"
                        , "CCTI", "Electrónica", "Industrial", "Mecánica", "Mecánica Industrial", "Mecatrónica"
                        , "Química", "Química Industrial", "Tecnologías de Audio")
            }
            4 -> {
                arrayListByDepartment = arrayListOf("Biología", "Bioquímica y Microbiología"
                        , "Biotecnología Molecular", "Comunicación y Letras", "Física"
                        , "Matemática Aplicada", "Nutrición", "Química", "Química Farmacéutica")

            }
            5 -> {
                arrayListByDepartment = arrayListOf( "Educación", "Física y la Matemática"
                        ,"Música", "Psicopedagogía", "Química y la Biología")
            }
            6 -> {
                arrayListByDepartment = arrayListOf("Antropología y Sociología", "Arqueología", "Psicología"
                        , "Relaciones Internacionales & Master of Arts in Global Affairs")
            }
            7 -> {
                arrayListByDepartment = arrayListOf("Composición y Producción Musical", "Diseño de Producto e Innovación")
            }
            8 -> {
                arrayListByDepartment = arrayListOf("Food Business and Marketing", "international Marketing and Business Analytics")
            }
            9 -> {
                arrayListByDepartment = arrayListOf("Baccalaureatus en Artibus", "Baccalaureatus en Scientiis")
            }
        }

        return arrayListByDepartment
    }
    private fun setupBottomNavigationView() {
        Log.d(DataInsertActivity.TAG, "SetupBottomNavigationView: settinn BottomNavView")
        val bottomNavigationViewEx = findViewById<BottomNavigationViewEx>(R.id.bottomNavViewBar)
        val bottomNavigationViewHelper = BottomNavigationViewHelperUser()
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        bottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx)
        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(DataInsertActivity.ACTIVITY_NUM)
        menuItem.isChecked = true
    }
    override fun onBackPressed() {
        startActivity(Intent(this, UserNewEventActivity::class.java))
    }


}
