package com.example.a4you

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.a4you.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
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
}