package com.student.mypractices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.NumberFormat

class TipCalculatorFragment : Fragment() {

    private lateinit var etBillAmount: EditText
    private lateinit var etTipPercentage: EditText
    private lateinit var etNumberOfPeople: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTotalPerPerson: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tip_calc, container, false)

        etBillAmount = view.findViewById(R.id.etBillAmount)
        etTipPercentage = view.findViewById(R.id.etTipPercentage)
        etNumberOfPeople = view.findViewById(R.id.etNumberOfPeople)
        btnCalculate = view.findViewById(R.id.btnCalculate)
        tvTipAmount = view.findViewById(R.id.tvTipAmount)
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount)
        tvTotalPerPerson = view.findViewById(R.id.tvTotalPerPerson)

        btnCalculate.setOnClickListener {
            calculateTip()
        }

        return view
    }

    private fun calculateTip() {
        val billAmount = etBillAmount.text.toString().toDoubleOrNull() ?: 0.0
        val tipPercentage = etTipPercentage.text.toString().toDoubleOrNull() ?: 0.0
        val numberOfPeople = etNumberOfPeople.text.toString().toIntOrNull() ?: 1

        val tipAmount = billAmount * (tipPercentage / 100)
        val totalAmount = billAmount + tipAmount
        val totalPerPerson = if (numberOfPeople > 0) totalAmount / numberOfPeople else 0.0

        val currencyFormat = NumberFormat.getCurrencyInstance()
        tvTipAmount.text = currencyFormat.format(tipAmount)
        tvTotalAmount.text = currencyFormat.format(totalAmount)
        tvTotalPerPerson.text = currencyFormat.format(totalPerPerson)
    }
}
