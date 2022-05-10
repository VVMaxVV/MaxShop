package com.maxshop.ui

import android.os.Bundle
import com.example.maxshop.R
import com.example.maxshop.databinding.ActivityMainBinding
import com.maxshop.fragment.CategoriesFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.fragmentContainer?.let {
            supportFragmentManager.beginTransaction().replace(it.id, CategoriesFragment()).commit()
        }
    }
}
