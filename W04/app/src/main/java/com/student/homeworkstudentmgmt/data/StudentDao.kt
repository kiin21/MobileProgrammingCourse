package com.student.homeworkstudentmgmt.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE id = :id")
    fun getStudentById(id: String): Student?
}