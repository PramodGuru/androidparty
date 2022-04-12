package com.pdguru.androidparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdguru.androidparty.model.LoginCredentials
import com.pdguru.androidparty.networking.RetrofitFactory
import com.pdguru.androidparty.networking.ServerInterface
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import timber.log.Timber

class MainActivityViewModel(client: OkHttpClient) : ViewModel(), KoinComponent {
    private var serverInterface: ServerInterface =
        RetrofitFactory.createRetrofit(client, BASE_URL).create(ServerInterface::class.java)

    fun login(username: String, password: String) {
        Timber.d("Logging in: $username:$password")

        viewModelScope.launch {
            val result = serverInterface.login(LoginCredentials(username, password))
            Timber.d("Response: ${result.body()?.token}")
        }

    }
}
