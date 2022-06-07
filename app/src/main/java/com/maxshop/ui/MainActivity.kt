package com.maxshop.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.maxshop.R
import com.example.maxshop.databinding.ActivityMainBinding
import com.maxshop.stream.BottomNavMenuStream
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var bottomNavMenuStream: BottomNavMenuStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.let {
            it.bottomMenuStream = bottomNavMenuStream
            it.lifecycleOwner = this
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding?.bottomNavBar?.setupWithNavController(navController)
    }
}
