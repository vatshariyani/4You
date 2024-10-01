package com.example.a4you

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

@Suppress("UNUSED_VARIABLE")
class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Get references to UI elements
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.button2)

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                // Sign in the user
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User signed in successfully
                            val user = task.result?.user
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                            // Navigate to the main activity or home screen
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle error (e.g., incorrect email or password)
                            Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
/*package com.example.a4you

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
*/