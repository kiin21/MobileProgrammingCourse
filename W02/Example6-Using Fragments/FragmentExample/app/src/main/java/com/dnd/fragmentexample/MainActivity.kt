package com.dnd.fragmentexample



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRssItemSelected(text: String?) {
        val fragment =  supportFragmentManager.findFragmentById(R.id.detailFragment) as DetailFragment
        fragment.setText(text!!)
    }
}
