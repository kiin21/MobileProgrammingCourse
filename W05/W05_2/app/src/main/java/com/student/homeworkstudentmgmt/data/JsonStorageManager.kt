package com.student.homeworkstudentmgmt.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

object JsonStorageManager {
    private const val STUDENT_FILE = "students.json"
    private val gson = Gson()

    // Save students to JSON file
    fun saveStudents(context: Context, students: List<Student>) {
        val json = gson.toJson(students)
        context.openFileOutput(STUDENT_FILE, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    // Load students from JSON file
    fun loadStudents(context: Context): List<Student> {
        return try {
            context.openFileInput(STUDENT_FILE).bufferedReader().use {
                val json = it.readText()
                val type = object : TypeToken<List<Student>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            }
        } catch (e: FileNotFoundException) {
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}
