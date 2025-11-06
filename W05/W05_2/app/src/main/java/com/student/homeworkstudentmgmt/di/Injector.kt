package com.student.homeworkstudentmgmt.di

import android.content.Context
import com.student.homeworkstudentmgmt.data.StudentRepository

object Injector {
    fun getStudentRepository(context: Context): StudentRepository {
        return StudentRepository(context.applicationContext)
    }
}
