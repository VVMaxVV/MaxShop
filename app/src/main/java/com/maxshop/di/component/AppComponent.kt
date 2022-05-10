package com.maxshop.di.component

import android.app.Application
import android.content.Context
import com.maxshop.MaxShopApplication
import com.maxshop.di.ActivityModule
import com.maxshop.di.ShopUIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ShopUIModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(application: MaxShopApplication)
}