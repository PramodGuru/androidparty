package com.pdguru.androidparty

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdguru.androidparty.model.LoginCredentials
import com.pdguru.androidparty.model.LoginToken
import com.pdguru.androidparty.networking.RetrofitFactory
import com.pdguru.androidparty.networking.ServerInterface
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import retrofit2.Response
import timber.log.Timber

class MainActivityViewModel(
        private val sharedPrefs: SharedPreferences,
        moshi: Moshi,
        client: OkHttpClient,
        private val resources: Resources) : ViewModel(),
        KoinComponent {

    private val _state = MutableLiveData<UIState>()
    val state = _state as LiveData<UIState>
    private val currentState: UIState
        get() {
            return state.value!!
        }

    init {
        _state.value = UIState()
    }


    private var serverInterface: ServerInterface =
            RetrofitFactory.createRetrofit(moshi, client, BASE_URL).create(ServerInterface::class.java)

    fun login(username: String, password: String) {
        _state.postValue(
                currentState.copy(message = resources.getString(R.string.wait), isProcessing = true)
        )
        Timber.d("Logging in: $username:$password")

        viewModelScope.launch {
            val result = serverInterface.login(LoginCredentials(username, password))
            handleResponse(result)
        }
    }

    private fun handleResponse(result: Response<LoginToken>) {
        if (result.code() == 200) {
            Timber.d("Response: ${result.body()?.token}")
            with(sharedPrefs.edit()) {
                putString(AUTH_TOKEN, result.body()!!.token)
                apply()
            }
            _state.postValue(
                    currentState.copy(message = resources.getString(R.string.success),
                            goNext = true, isProcessing = false))
        } else {
            Timber.d("Error: ${result.code()} +++ Response: ${result.errorBody()}")
            _state.postValue(
                    currentState.copy(message = resources.getString(R.string.failure),
                            isProcessing = false))
        }
    }

    data class UIState(
            val message: String? = null,
            val isProcessing: Boolean = false,
            val goNext: Boolean = false
    )
}
