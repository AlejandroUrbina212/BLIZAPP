package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<View>(R.id.registerBtn) as Button
        registerButton.setOnClickListener(View.OnClickListener {
            registerUser()
        })
    }


    private fun registerUser () {
        val nameTxt = findViewById<View>(R.id.nameTxt) as EditText
        val lastNameTxt = findViewById<View>(R.id.lastNameTxt) as EditText
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText

        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        val passwordConfirmationTxt = findViewById<View>(R.id.passwordConfirmationTxt) as EditText

        val name = nameTxt.text.toString()
        val lastName = lastNameTxt.text.toString()
        val email = emailTxt.text.toString()
        val password = passwordTxt.text.toString()
        val passwordConfirmation = passwordConfirmationTxt.text.toString()


        if (!email.isEmpty() && !password.isEmpty() && password==passwordConfirmation && !name.isEmpty() && !lastName.isEmpty()) {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    //See how to use FireStore ----- mDatabase.child(uid).child("Name").setValue(name)

                    val newUser = User(uid,password,name,lastName,email)

                    db.collection("users")
                            .document(uid)
                            //.set(items)
                            .set(newUser)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Successfully registered :)", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, LoginActivity::class.java))

                            }.addOnFailureListener { exception: Exception ->
                                Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                            }

                    //startActivity(Intent(this, LoginActivity::class.java))

                }else {
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }


}
