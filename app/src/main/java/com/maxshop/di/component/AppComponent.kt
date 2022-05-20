package com.maxshop.di.component

import android.content.Context
import com.maxshop.MaxShopApplication
import com.maxshop.di.ActivityModule
import com.maxshop.di.BagModule
import com.maxshop.di.CoreModule
import com.maxshop.di.FavoriteModule
import com.maxshop.di.HomeModule
import com.maxshop.di.ProfileModule
import com.maxshop.di.ShopModule
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
        CoreModule::class,
        HomeModule::class,
        ShopModule::class,
        BagModule::class,
        FavoriteModule::class,
        ProfileModule::class,
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(application: MaxShopApplication)
}
