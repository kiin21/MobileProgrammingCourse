package com.student.myactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var buttonToActivity2: Button
    private lateinit var timestampFromActivity2TextView: TextView

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val timestamp = result.data?.extras?.getLong("timestamp", 0L)
            if (timestamp != null && timestamp != 0L) {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
                val netDate = Date(timestamp)
                timestampFromActivity2TextView.text = "Timestamp from Activity 2: ${sdf.format(netDate)}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonToActivity2 = findViewById(R.id.button_to_activity2)
        timestampFromActivity2TextView = findViewById(R.id.textview_timestamp_from_2)

        buttonToActivity2.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            intent.putExtra("timestamp", System.currentTimeMillis())
            startForResult.launch(intent)
        }
    }
}