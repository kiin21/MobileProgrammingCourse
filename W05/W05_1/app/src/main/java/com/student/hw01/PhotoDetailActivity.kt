package com.student.hw01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.student.hw01.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoUri = intent.getStringExtra("PHOTO_URI")

        if (photoUri != null) {
            Glide.with(this)
                .load(photoUri)
                .fitCenter()
                .into(binding.ivPhotoDetail)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
