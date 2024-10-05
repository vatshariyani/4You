package com.example.a4you

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Get references to UI elements
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword2)
        val signupButton = findViewById<Button>(R.id.button4)
        val usernameEditText = findViewById<EditText>(R.id.editTextText)  // New EditText for username

        // Set up signup button click listener
        signupButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()

            // Validate input (optional)
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                // Create a new user account
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User account created successfully
                            val user = task.result?.user
                            val userId = user?.uid
                            // Save username to Firebase Realtime Database
                            val usernameRef = database.reference.child("users").child(userId!!)
                            usernameRef.child("username").setValue(username)
                                .addOnSuccessListener {
                                    Toast.makeText(this@SignupActivity, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                    // Navigate to the next activity or screen
                                    startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this@SignupActivity, "Failed to save username: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }else {
                            // Handle error (e.g., email already exists, weak password)
                            Toast.makeText(this@SignupActivity, "Failed to create account: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

/*package com.example.a4you

import android.os.Bundle
import androidx.activity.ComponentActivity

class SignupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

    }
}
*/