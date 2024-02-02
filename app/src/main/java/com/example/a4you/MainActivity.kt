@file:Suppress("UnusedImport")

package com.example.a4you

import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a4you.ui.theme._4YouTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    // Firebase authentication instance
    private lateinit var firebaseAuth: FirebaseAuth

    // Activity result launcher for Google Sign-In
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is already signed in
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // User is already signed in, redirect to HomeActivity
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish() // Finish MainActivity to prevent returning to it when pressing back
            return // Exit onCreate method
        }

        //Initialize the signInLauncher
        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // If the result is OK, process the Google Sign-In
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        // Sign in with Google
                        firebaseAuthWithGoogle(account.idToken!!)
                    } else {
                        // Handle the case where the Google account is null
                        Toast.makeText(this, "User account not found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: ApiException) {
                    // Handle ApiException
                    Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // storing ID of the button
        // in a variable
        val button = findViewById<Button>(R.id.button)
        val button1 = findViewById<Button>(R.id.button1)
        val button0 = findViewById<Button>(R.id.button0)

        // operations to be performed
        // when user tap on the button
        button?.setOnClickListener()
        {
            // displaying a toast message
            Toast.makeText(this@MainActivity, R.string.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        button0?.setOnClickListener()
        {
            // displaying a toast message
            Toast.makeText(this@MainActivity, R.string.message0, Toast.LENGTH_LONG).show()
            val signInIntent = getGoogleSignInIntent() // Obtain the signInIntent here
            signInLauncher.launch(signInIntent)
        }
        button1?.setOnClickListener()
        {
            // displaying a toast message
            Toast.makeText(this@MainActivity, R.string.message1, Toast.LENGTH_LONG).show()
        }

    }

    // Function to create Google Sign-In intent
    private fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        return mGoogleSignInClient.signInIntent
    }

    // Function to sign in with Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Google sign in success", Toast.LENGTH_SHORT).show()
                    // Proceed to the next activity or perform other actions
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Google sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

