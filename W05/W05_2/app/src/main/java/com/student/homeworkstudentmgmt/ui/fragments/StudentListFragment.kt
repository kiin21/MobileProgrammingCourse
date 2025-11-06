package com.student.homeworkstudentmgmt.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.student.homeworkstudentmgmt.R
import com.student.homeworkstudentmgmt.data.Student
import com.student.homeworkstudentmgmt.data.StudentRepository
import com.student.homeworkstudentmgmt.databinding.FragmentStudentListBinding
import com.student.homeworkstudentmgmt.di.Injector
import com.student.homeworkstudentmgmt.ui.adapters.StudentAdapter
import kotlinx.coroutines.launch

class StudentListFragment : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentRepository: StudentRepository
    private lateinit var adapter: StudentAdapter
    private var isGridLayout = false
    private var allStudents: List<Student> = emptyList()

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
            navigateToDetail(student)
        }

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@StudentListFragment.adapter
        }
    }

    private fun loadStudents() {
        lifecycleScope.launch {
            allStudents = studentRepository.getAllStudents()
            setupAutoCompleteSearch()
            setupClassSpinner()
            filterAndDisplayStudents()
        }
    }

    private fun setupListeners() {
        binding.btnAddStudent.setOnClickListener {
            navigateToDetail(null) // null = create new
        }

        binding.btnToggleLayout.setOnClickListener {
            isGridLayout = !isGridLayout
            updateLayoutManager()
        }
    }

    private fun setupAutoCompleteSearch() {
        val studentNames = allStudents.map { it.fullName }
        val searchAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, studentNames)
        binding.actvSearch.setAdapter(searchAdapter)

        binding.actvSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterAndDisplayStudents()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClassSpinner() {
        val classes = allStudents.map { it.className }.distinct()
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listOf("All Classes") + classes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerClass.adapter = spinnerAdapter

        binding.spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterAndDisplayStudents()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterAndDisplayStudents() {
        val searchQuery = binding.actvSearch.text.toString()
        val selectedClass = binding.spinnerClass.selectedItem.toString()

        lifecycleScope.launch {
            val filteredStudents = allStudents.filter { student ->
                val nameMatches = student.fullName.contains(searchQuery, ignoreCase = true)
                val classMatches = selectedClass == "All Classes" || student.className == selectedClass
                nameMatches && classMatches
            }
            adapter.submitList(filteredStudents)
            updateEmptyState(filteredStudents.isEmpty())
        }
    }

    private fun updateLayoutManager() {
        if (isGridLayout) {
            binding.rvStudents.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.btnToggleLayout.setImageResource(R.drawable.ic_linear_layout)
        } else {
            binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
            binding.btnToggleLayout.setImageResource(R.drawable.ic_grid_layout)
        }
    }

    private fun navigateToDetail(student: Student?) {
        val fragment = StudentDetailFragment.newInstance(student)
        (requireActivity() as com.student.homeworkstudentmgmt.MainActivity).loadFragment(fragment)
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.tvEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}