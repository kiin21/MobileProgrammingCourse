package com.student.homeworkstudentmgmt.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.student.homeworkstudentmgmt.data.Student

class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }
}