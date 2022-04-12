package com.pdguru.androidparty

import android.app.Application
import com.pdguru.androidparty.di.KoinGraph
import com.pdguru.androidparty.logging.LoggingTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AndroidPartyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        plantTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@AndroidPartyApp)
            modules(KoinGraph.appModules())
        }
    }

    private fun plantTimber(){
        Timber.plant(LoggingTree(resources.getString(R.string.app_name)))
        Timber.d("Timber is planted")
    }
}
