package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.app.blizapp.blizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginButton = findViewById<View>(R.id.loginBtn) as Button
        loginButton.setOnClickListener {
            login()
        }

        val registerUserButton = findViewById<View>(R.id.registerUserBtn) as Button
        registerUserButton.setOnClickListener {
            goToRegister()
        }

    }

    private fun login() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        var email = emailTxt.text.toString()
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        var password = passwordTxt.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    redirectToDataInsert()
                    Toast.makeText(this, "Redirigiendo a Ingreso de Datos", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No hay un usario con los datos especificados :|", Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            Toast.makeText(this, "Por favor llene las credenciales :|", Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectToDataInsert() {
        val usersRef = FirebaseFirestore.getInstance().collection("users").document(mAuth.currentUser!!.uid)

        usersRef.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            Log.d("USER TYPE", "DocumentSnapshot data: " + task.result.data)
                            startActivity(Intent(this, DataInsertActivity::class.java))
                            Toast.makeText(this, "Login as user :)", Toast.LENGTH_LONG).show()
                            this.finish()
                        }
                    } else {
                        Log.d("USER TYPE", "get failed with ", task.exception)
                    }
                }
    }

    private fun goToRegister(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

}
