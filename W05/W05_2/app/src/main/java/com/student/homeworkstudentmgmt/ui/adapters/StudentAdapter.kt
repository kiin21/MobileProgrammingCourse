package com.student.homeworkstudentmgmt.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.student.homeworkstudentmgmt.R
import com.student.homeworkstudentmgmt.data.Student
import com.student.homeworkstudentmgmt.databinding.ItemStudentBinding

class StudentAdapter(
    private val onStudentClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.tvAvatar.text = student.getInitials()
            binding.tvStudentName.text = student.fullName
            binding.tvClassName.text = student.className
            binding.tvBirthdayGender.text = binding.root.context.getString(R.string.birthday_gender_format, student.birthday, student.gender)

            binding.root.setOnClickListener {
                onStudentClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding =
            ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }
}