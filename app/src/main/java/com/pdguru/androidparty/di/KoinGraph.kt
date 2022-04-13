package com.pdguru.androidparty.di

import com.pdguru.androidparty.BASE_URL
import com.pdguru.androidparty.MainActivityViewModel
import com.pdguru.androidparty.networking.HttpClientProvider
import com.pdguru.androidparty.networking.MoshiFactory
import com.pdguru.androidparty.networking.RetrofitFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinGraph {
    private val clientProvider = HttpClientProvider()

    fun appModules(): List<Module> {
        return listOf(
            networkModule,
            baseModule
        )
    }

    private val baseModule = module {
        single { androidApplication().resources }

        viewModel { MainActivityViewModel(get(), clientProvider.getOkHttpClient()) }
    }

    private val networkModule = module {
        single { MoshiFactory.createMoshi() }
        single { RetrofitFactory.createRetrofit(get(), clientProvider.getOkHttpClient(), BASE_URL) }
    }
}
