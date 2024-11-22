package com.ayukrisna.dicodingstory

import android.app.Application
import com.ayukrisna.dicodingstory.di.dataStoreModule
import com.ayukrisna.dicodingstory.di.preferenceModule
import com.ayukrisna.dicodingstory.di.repositoryModules
import com.ayukrisna.dicodingstory.di.useCaseModules
import com.ayukrisna.dicodingstory.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    dataStoreModule,
                    preferenceModule,
                    repositoryModules,
                    useCaseModules,
                    viewModelModules
                )
            )
        }
    }
}