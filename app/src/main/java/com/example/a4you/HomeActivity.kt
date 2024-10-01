package com.example.a4you

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.a4you.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // Initialize Firebase Authentication and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Get reference to the username text view
        val txtUsername = findViewById<TextView>(R.id.txt_username)

        // Retrieve username from Firebase Realtime Database
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val usernameRef = database.reference.child("users").child(userId).child("username")
            usernameRef.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val username = dataSnapshot.getValue(String::class.java)
                    if (username != null)
                    {
                        txtUsername.text = "Welcome, $username!"
                    } else {
                        Toast.makeText(this@HomeActivity, "Failed to retrieve username", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@HomeActivity, "Failed to retrieve username: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@HomeActivity, "User not logged in", Toast.LENGTH_SHORT).show()
        }

        binding.button3.setOnClickListener { // Assuming you have a button with id button3 for sign out
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            //val email = currentUser.email
            val userName = currentUser.displayName

            //binding.txtEmail.text = email
            binding.txtUsername.text = userName
        }

        binding.button3.setOnClickListener {
            signOut()
        }
    }
    private fun signOut() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}*/