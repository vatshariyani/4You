package com.example.a4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.a4you.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val email = currentUser.email
            val userName = currentUser.displayName

            binding.txtEmail.text = email
            binding.txtUsername.text = userName
        }
    }
}