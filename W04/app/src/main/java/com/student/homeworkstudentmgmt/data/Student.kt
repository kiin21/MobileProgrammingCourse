package com.student.homeworkstudentmgmt.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var fullName: String,
    var birthday: String,
    var className: String,
    var gender: String
) : Parcelable {
    fun getInitials(): String {
        return fullName.split(" ")
            .take(2)
            .joinToString("") { it.first().uppercase() }
    }
}