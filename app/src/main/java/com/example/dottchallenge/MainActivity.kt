package com.example.dottchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dottchallenge.common.utils.replaceFragment
import com.example.dottchallenge.map.ui.MapsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentById(android.R.id.content) != null) return

        replaceFragment(MapsFragment.create())
    }
}
