@file:Suppress("UnusedImport")

package com.example.a4you

import android.widget.Button
import android.widget.Toast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a4you.ui.theme._4YouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        }
        button0?.setOnClickListener()
        {
            // displaying a toast message
            Toast.makeText(this@MainActivity, R.string.message0, Toast.LENGTH_LONG).show()
        }
        button1?.setOnClickListener()
        {
            // displaying a toast message
            Toast.makeText(this@MainActivity, R.string.message1, Toast.LENGTH_LONG).show()
        }
    }
}

