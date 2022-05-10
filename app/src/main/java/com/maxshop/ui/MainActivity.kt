package com.maxshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maxshop.R
import com.maxshop.shop_ui.ShopFragment
import dagger.android.DaggerActivity
import javax.inject.Inject

class MainActivity : DaggerActivity() {
    @Inject
    lateinit var shopFragment: ShopFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopFragment.test()
    }
}