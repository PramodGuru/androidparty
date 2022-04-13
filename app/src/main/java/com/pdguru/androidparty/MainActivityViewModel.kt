package com.pdguru.androidparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdguru.androidparty.model.LoginCredentials
import com.pdguru.androidparty.networking.RetrofitFactory
import com.pdguru.androidparty.networking.ServerInterface
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import timber.log.Timber

class MainActivityViewModel(private val moshi: Moshi, client: OkHttpClient) : ViewModel(),
    KoinComponent {
    private var serverInterface: ServerInterface =
        RetrofitFactory.createRetrofit(moshi, client, BASE_URL).create(ServerInterface::class.java)

    fun login(username: String, password: String) {
        Timber.d("Logging in: $username:$password")

        val loginCred =
            moshi.adapter(LoginCredentials::class.java).toJson(LoginCredentials(username, password))
        viewModelScope.launch {
            val result = serverInterface.login(loginCred)
            Timber.d("Response: ${result.body()}")
        }

    }
}
