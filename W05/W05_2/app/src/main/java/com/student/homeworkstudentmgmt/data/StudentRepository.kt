package com.student.homeworkstudentmgmt.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentRepository(private val context: Context) {
    private var studentsCache: MutableList<Student> = mutableListOf()

    init {
        studentsCache = JsonStorageManager.loadStudents(context).toMutableList()
    }

    // Get all students
    suspend fun getAllStudents(): List<Student> {
        return withContext(Dispatchers.IO) {
            studentsCache.toList()
        }
    }

    // Save/insert a student
    suspend fun saveStudent(student: Student) {
        withContext(Dispatchers.IO) {
            val existingIndex = studentsCache.indexOfFirst { it.id == student.id }
            if (existingIndex >= 0) {
                studentsCache[existingIndex] = student
            } else {
                studentsCache.add(student)
            }
            JsonStorageManager.saveStudents(context, studentsCache)
        }
    }

    // Update a student
    suspend fun updateStudent(student: Student) {
        saveStudent(student) // Same as save
    }

    // Delete a student
    suspend fun deleteStudent(student: Student) {
        withContext(Dispatchers.IO) {
            studentsCache.removeIf { it.id == student.id }
            JsonStorageManager.saveStudents(context, studentsCache)
        }
    }

}
