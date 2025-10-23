package com.student.mypractices

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment

class CoffeeOrderFragment : Fragment() {

    private companion object {
        const val PREFS_NAME = "CoffeeOrderPrefs"
        const val KEY_COFFEE_TYPE = "coffee_type"
        const val KEY_CREAM_CHECKED = "cream_checked"
        const val KEY_SUGAR_CHECKED = "sugar_checked"
        const val KEY_RESULT_TEXT = "result_text"
    }

    private lateinit var radioGroupCoffeeType: RadioGroup
    private lateinit var cbCream: CheckBox
    private lateinit var cbSugar: CheckBox
    private lateinit var tvResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroupCoffeeType = view.findViewById(R.id.radio_group_coffee_type)
        cbCream = view.findViewById(R.id.cbCream)
        cbSugar = view.findViewById(R.id.cbSugar)
        tvResult = view.findViewById(R.id.tvResult)

        loadSavedState()

        view.findViewById<Button>(R.id.btnPay).setOnClickListener {
            processOrder()
        }
    }

    private fun processOrder() {
        val coffeeType = when (radioGroupCoffeeType.checkedRadioButtonId) {
            R.id.rbDecaf -> "Decaf"
            R.id.rbExpresso -> "Expresso"
            R.id.rbColombian -> "Colombian"
            else -> "Colombian"
        }

        val toppings = buildList {
            if (cbCream.isChecked) add("Cream")
            if (cbSugar.isChecked) add("Sugar")
        }

        val toppingsString = toppings.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "None"
        val resultString = "Order: $coffeeType | Toppings: $toppingsString"

        tvResult.text = resultString
        saveCurrentState()
        Toast.makeText(requireContext(), "Thanks!", Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedState() {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        radioGroupCoffeeType.check(prefs.getInt(KEY_COFFEE_TYPE, R.id.rbColombian))
        cbCream.isChecked = prefs.getBoolean(KEY_CREAM_CHECKED, false)
        cbSugar.isChecked = prefs.getBoolean(KEY_SUGAR_CHECKED, false)
        tvResult.text = prefs.getString(KEY_RESULT_TEXT, "")
    }

    override fun onStop() {
        super.onStop()
        saveCurrentState()
    }

    private fun saveCurrentState() {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // Sử dụng KTX extension function
        prefs.edit {
            putInt(KEY_COFFEE_TYPE, radioGroupCoffeeType.checkedRadioButtonId)
            putBoolean(KEY_CREAM_CHECKED, cbCream.isChecked)
            putBoolean(KEY_SUGAR_CHECKED, cbSugar.isChecked)
            putString(KEY_RESULT_TEXT, tvResult.text.toString())
        }
    }
}