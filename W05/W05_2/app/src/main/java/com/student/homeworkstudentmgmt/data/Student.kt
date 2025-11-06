package com.student.homeworkstudentmgmt.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Student(
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