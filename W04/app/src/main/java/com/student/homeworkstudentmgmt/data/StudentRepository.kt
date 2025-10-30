package com.student.homeworkstudentmgmt.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun saveStudent(student: Student) {
        withContext(Dispatchers.IO) {
            studentDao.insert(student)
        }
    }

    suspend fun loadStudents(): List<Student> {
        return withContext(Dispatchers.IO) {
            studentDao.getAllStudents()
        }
    }

    suspend fun updateStudent(student: Student) {
        withContext(Dispatchers.IO) {
            studentDao.update(student)
        }
    }

    suspend fun deleteStudent(student: Student) {
        withContext(Dispatchers.IO) {
            studentDao.delete(student)
        }
    }
}