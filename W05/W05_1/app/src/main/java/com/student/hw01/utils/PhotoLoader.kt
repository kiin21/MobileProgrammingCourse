package com.student.hw01.utils

import android.content.Context
import android.provider.MediaStore
import com.student.hw01.models.Photo

object PhotoLoader {
    fun loadPhotos(context: Context): List<Photo> {
        val photoList = mutableListOf<Photo>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATA
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        
        // Query MediaStore for all images
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use { c ->
            val idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val dataColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                val name = c.getString(nameColumn)
                val dateAdded = c.getLong(dateColumn)
                val path = c.getString(dataColumn)

                photoList.add(Photo(id, path, name, dateAdded))
            }
        }
        
        return photoList
    }
}
