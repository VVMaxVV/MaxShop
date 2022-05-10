package com.maxshop

import android.app.Application
import com.maxshop.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


class MaxShopApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    private val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}