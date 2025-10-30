package com.student.homeworkstudentmgmt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.student.homeworkstudentmgmt.di.Injector
import com.student.homeworkstudentmgmt.data.Student
import com.student.homeworkstudentmgmt.data.StudentRepository
import com.student.homeworkstudentmgmt.databinding.FragmentStudentListBinding
import com.student.homeworkstudentmgmt.ui.adapters.StudentAdapter
import kotlinx.coroutines.launch

class StudentListFragment : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentRepository: StudentRepository
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRepository = Injector.getStudentRepository(requireContext())
        setupRecyclerView()
        loadStudents()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter { student ->
            // Navigate to detail fragment
            navigateToDetail(student)
        }

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@StudentListFragment.adapter
        }
    }

    private fun loadStudents() {
        lifecycleScope.launch {
            val loadedStudents = studentRepository.loadStudents()
            adapter.submitList(loadedStudents)
            updateEmptyState(loadedStudents.isEmpty())
        }
    }

    private fun setupListeners() {
        binding.btnAddStudent.setOnClickListener {
            navigateToDetail(null) // null = create new
        }
    }

    private fun navigateToDetail(student: Student?) {
        val fragment = StudentDetailFragment.newInstance(student)
        (requireActivity() as com.student.homeworkstudentmgmt.ui.MainActivity).loadFragment(fragment)
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.tvEmptyState.visibility =
            if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}