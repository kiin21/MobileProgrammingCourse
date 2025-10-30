package com.student.homeworkstudentmgmt.di

import android.content.Context
import com.student.homeworkstudentmgmt.data.StudentDatabase
import com.student.homeworkstudentmgmt.data.StudentRepository

object Injector {
    fun getStudentRepository(context: Context): StudentRepository {
        val database = StudentDatabase.getDatabase(context)
        return StudentRepository(database.studentDao())
    }
}