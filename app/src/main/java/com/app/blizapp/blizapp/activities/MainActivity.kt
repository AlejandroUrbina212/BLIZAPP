package com.app.blizapp.blizapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.blizapp.blizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3500)
                    if (mAuth.currentUser != null) {

                        redirectByRole()
                        return
                    }
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }

    private fun redirectByRole(){
        val usersRef = FirebaseFirestore.getInstance().collection("users").document(mAuth.currentUser!!.uid)

        usersRef.get()
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            Log.d("USER TYPE", "DocumentSnapshot data: " + task.result.data)
                            startActivity(Intent(this, UserEventsActivity::class.java))
                            Toast.makeText(this, "Login como usuario :)", Toast.LENGTH_LONG).show()
                            this.finish()
                        } else {
                            Log.d("USER TYPE", "No such document")
                            startActivity(Intent(this, StudentFeedActivity::class.java))
                            Toast.makeText(this, "Login como courier :)", Toast.LENGTH_LONG).show()
                            this.finish()
                        }
                    } else {
                        Log.d("USER TYPE", "get failed with ", task.exception)
                    }
                }
    }
}
