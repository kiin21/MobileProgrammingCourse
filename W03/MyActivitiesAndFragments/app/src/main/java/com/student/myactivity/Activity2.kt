package com.student.myactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.student.myactivity.R

class Activity2 : AppCompatActivity(), Activity2Fragment.OnActivity2FragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge for Activity2
        setContentView(R.layout.activity_2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            val fragment = Activity2Fragment()
            fragment.arguments = intent.extras
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onBackButtonPressed(timestamp: Long) {
        val resultIntent = Intent()
        resultIntent.putExtra("timestamp", timestamp)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
