package com.example.a4you

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Additional initialization code for the login activity can be added here
        val editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val button2 = findViewById<Button>(R.id.button2)

        fun login(email: String, password: String) {
            if(email == "admin@4you.com"){
                if (password == "4YouAdmin"){
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
                else{
                    Toast.makeText(this@LoginActivity, R.string.incorrect_password, Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this@LoginActivity, R.string.incorrect_email_id, Toast.LENGTH_LONG).show()
            }
        }

        button2?.setOnClickListener()
        {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPassword.text.toString()
            login(email, password)
        }
    }
}
