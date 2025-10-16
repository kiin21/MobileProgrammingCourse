package com.dnd.fragmentexample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ListFragment : Fragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        view.findViewById<Button>(R.id.updateBtn).setOnClickListener {
            updateDetail("testing...")
        }

        return view
    }

    private var listener: OnItemSelectedListener? = null
    interface OnItemSelectedListener {
        fun onRssItemSelected(text: String?)
    }
    // triggers update of the details fragment
    fun updateDetail(detail: String) {
        // create fake data
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val today = formatter.format(Date())

        // send data to activity
        listener!!.onRssItemSelected(detail + " " + today)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = if (context is OnItemSelectedListener) {
            context
        } else {
            throw ClassCastException(
                context.toString()
                    .toString() + " must implement ListFragment.OnItemSelectedListener"
            )
        }
    }

}
