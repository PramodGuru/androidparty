package com.pdguru.androidparty.servers

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdguru.androidparty.AUTH_TOKEN
import com.pdguru.androidparty.BASE_URL
import com.pdguru.androidparty.R
import com.pdguru.androidparty.model.ServerLocations
import com.pdguru.androidparty.networking.RetrofitFactory
import com.pdguru.androidparty.networking.ServerInterface
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import retrofit2.Response
import timber.log.Timber

class ServerActivityViewModel(private val sharedPrefs: SharedPreferences,
                              moshi: Moshi,
                              client: OkHttpClient,
                              private val resources: Resources) :
        ViewModel(), KoinComponent {

    private var serverInterface: ServerInterface =
            RetrofitFactory.createRetrofit(moshi, client, BASE_URL).create(ServerInterface::class.java)

    private val _state = MutableLiveData<UIState>()
    val state = _state as LiveData<UIState>
    private val currentState: UIState
        get() {
            return state.value!!
        }

    init {
        _state.value = UIState()
    }

    private fun getAuthToken(): String? {
        return sharedPrefs.getString(AUTH_TOKEN, null)
    }

    fun getServerList() {
        viewModelScope.launch {
            val result = serverInterface.getServers(token = "Bearer ${getAuthToken()}")
            handleResponse(result)
        }
    }

    fun logout() {
        with(sharedPrefs.edit()) {
            putString(AUTH_TOKEN, null)
            apply()
        }
        _state.postValue(currentState.copy(logout = true))
    }

    private fun handleResponse(result: Response<List<ServerLocations>>) {
        if (result.isSuccessful) {
            result.body()?.forEach { it ->
                Timber.d("SERVER: ${it.name}|${it.distance}")
            }
            Timber.d("SERVERLIST: ${result.body()?.toList()}")
            _state.postValue(
                    currentState.copy(serverList = result.body()?.toList())
            )
        } else {
            Timber.d("Error: ${result.code()} +++ Response: ${result.errorBody()}")
            _state.postValue(currentState.copy(message = resources.getString(R.string.failure)))
        }
    }

    data class UIState(
            val message: String? = null,
            val serverList: List<ServerLocations>? = null,
            val logout: Boolean = false
    )

}
