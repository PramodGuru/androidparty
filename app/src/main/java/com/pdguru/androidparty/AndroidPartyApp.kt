package com.pdguru.androidparty

import android.app.Application
import com.pdguru.androidparty.logging.LoggingTree
import timber.log.Timber

class AndroidPartyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggingTree(resources.getString(R.string.app_name)))
        Timber.d("Timber is planted")
    }
}
