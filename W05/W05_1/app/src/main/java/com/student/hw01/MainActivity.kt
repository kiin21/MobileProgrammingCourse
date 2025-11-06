package com.student.hw01

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.student.hw01.adapters.PhotoAdapter
import com.student.hw01.databinding.ActivityMainBinding
import com.student.hw01.utils.PhotoLoader

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadPhotos()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPhotos.layoutManager = GridLayoutManager(this, 3)

        checkPermissionAndLoadPhotos()
    }
    
    private fun checkPermissionAndLoadPhotos() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES  // Android 13+
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE  // Android 12 and below
        }

        when {
            ContextCompat.checkSelfPermission(this, permission) == 
                PackageManager.PERMISSION_GRANTED -> {
                loadPhotos()
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }
    
    private fun loadPhotos() {
        val photos = PhotoLoader.loadPhotos(this)
        
        if (photos.isEmpty()) {
            binding.rvPhotos.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            // Photos found - show RecyclerView
            binding.rvPhotos.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            
            // Create adapter with click listener
            val adapter = PhotoAdapter(photos) { photo ->
                // When a photo is clicked, open detail activity
                openPhotoDetail(photo.uri)
            }
            
            // Set adapter to RecyclerView
            binding.rvPhotos.adapter = adapter
        }
    }
    
    private fun openPhotoDetail(photoUri: String) {
        // Create Intent to open PhotoDetailActivity
        val intent = Intent(this, PhotoDetailActivity::class.java)
        intent.putExtra("PHOTO_URI", photoUri)
        startActivity(intent)
    }
}
