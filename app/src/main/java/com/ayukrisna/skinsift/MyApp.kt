package com.ayukrisna.skinsift

import android.app.Application
import com.ayukrisna.skinsift.di.dataStoreModule
import com.ayukrisna.skinsift.di.preferenceModule
import com.ayukrisna.skinsift.di.repositoryModules
import com.ayukrisna.skinsift.di.useCaseModules
import com.ayukrisna.skinsift.di.viewModelModules
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