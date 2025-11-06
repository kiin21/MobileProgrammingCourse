package com.student.homeworkstudentmgmt.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.core.os.BundleCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.student.homeworkstudentmgmt.di.Injector
import com.student.homeworkstudentmgmt.R
import com.student.homeworkstudentmgmt.data.Student
import com.student.homeworkstudentmgmt.data.StudentRepository
import com.student.homeworkstudentmgmt.databinding.FragmentStudentDetailBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StudentDetailFragment : Fragment() {
    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentRepository: StudentRepository
    private var currentStudent: Student? = null
    private var isEditMode = false

    companion object {
        private const val ARG_STUDENT = "student"

        fun newInstance(student: Student?): StudentDetailFragment {
            return StudentDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STUDENT, student)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRepository = Injector.getStudentRepository(requireContext())
        currentStudent = arguments?.let { BundleCompat.getParcelable(it, ARG_STUDENT, Student::class.java) }
        isEditMode = currentStudent != null

        setupSpinner()
        loadStudentData()
        setupListeners()
        updateButtonVisibility()
    }

    private fun setupSpinner() {
        val classes = resources.getStringArray(R.array.class_names).toList()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, classes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerClass.adapter = adapter
    }

    private fun loadStudentData() {
        currentStudent?.let { student ->
            binding.etFullName.setText(student.fullName)
            binding.etBirthday.setText(student.birthday)

            // Set spinner selection
            @Suppress("UNCHECKED_CAST")
            val adapter = binding.spinnerClass.adapter as? ArrayAdapter<String>
            adapter?.let {
                val classPosition = it.getPosition(student.className)
                binding.spinnerClass.setSelection(classPosition)
            }

            // Set gender
            when (student.gender) {
                "Male" -> binding.rbMale.isChecked = true
                "Female" -> binding.rbFemale.isChecked = true
                else -> binding.rbOther.isChecked = true
            }
        }
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            saveStudent()
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }

        binding.etBirthday.setOnClickListener {
            showDatePicker()
        }
    }

    private fun saveStudent() {
        lifecycleScope.launch {
            val fullName = binding.etFullName.text.toString().trim()
            val birthday = binding.etBirthday.text.toString().trim()
            val className = binding.spinnerClass.selectedItem.toString()
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> "Other"
            }

            if (!validateInput(fullName, birthday)) return@launch

            val student = if (isEditMode) {
                currentStudent!!.apply {
                    this.fullName = fullName
                    this.birthday = birthday
                    this.className = className
                    this.gender = gender
                }
            } else {
                Student(
                    fullName = fullName,
                    birthday = birthday,
                    className = className,
                    gender = gender
                )
            }

            if (isEditMode) {
                studentRepository.updateStudent(student)
            }
            else {
                studentRepository.saveStudent(student)
            }

            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun validateInput(fullName: String, birthday: String): Boolean {
        if (fullName.isEmpty()) {
            binding.etFullName.error = "Name is required"
            return false
        }
        if (birthday.isEmpty()) {
            binding.etBirthday.error = "Birthday is required"
            return false
        }
        return true
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteStudent() {
        lifecycleScope.launch {
            currentStudent?.let {
                studentRepository.deleteStudent(it)
                Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.etBirthday.setText(sdf.format(it))
        }

        picker.show(parentFragmentManager, picker.toString())
    }

    private fun updateButtonVisibility() {
        binding.btnDelete.visibility = if (isEditMode) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}