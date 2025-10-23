package com.student.myactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Activity2Fragment : Fragment() {

    private lateinit var timestampTextView: TextView
    private lateinit var backButton: Button
    private var listener: OnActivity2FragmentInteractionListener? = null

    interface OnActivity2FragmentInteractionListener {
        fun onBackButtonPressed(timestamp: Long)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActivity2FragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnActivity2FragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timestampTextView = view.findViewById(R.id.textview_timestamp_from_1)
        backButton = view.findViewById(R.id.button_back_to_activity1)

        arguments?.let { bundle ->
            val timestamp = bundle.getLong("timestamp", 0L)
            if (timestamp != 0L) {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
                val netDate = Date(timestamp)
                timestampTextView.text = "Timestamp from Activity 1: ${sdf.format(netDate)}"
            }
        }

        backButton.setOnClickListener {
            listener?.onBackButtonPressed(System.currentTimeMillis())
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}