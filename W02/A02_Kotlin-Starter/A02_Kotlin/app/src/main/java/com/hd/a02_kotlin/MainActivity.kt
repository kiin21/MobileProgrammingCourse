package com.hd.a02_kotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    val usaDf = DecimalFormat("###,###,###,###.##");
    val usd2cad = 1.32
    val usd2vnd = 23172.50

    //Your code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Your code
    }

    private  fun  shareIntent(){
        val sendIntent: Intent = Intent().apply {
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Text")
        startActivity(shareIntent)
    }
    private fun openContact() {
        val myIntent: Intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("content://contacts/people")
        )
        startActivity(myIntent)
    }
}