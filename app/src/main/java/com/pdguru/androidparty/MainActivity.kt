package com.pdguru.androidparty

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdguru.androidparty.databinding.ActivityMainBinding
import com.pdguru.androidparty.logging.LoggingTree
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plantTimber()
        setupDataBinding()
        setInteractors()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun setInteractors() {
        val loginButton = findViewById<Button>(R.id.btn_login)
        val username = findViewById<EditText>(R.id.input_username)
        val password = findViewById<EditText>(R.id.input_password)


            loginButton.setOnClickListener {
                if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                    Timber.d("button pressed")
                    Timber.d("u:p = ${username.text}:${password.text}")
                    login(username.text.toString(), password.text.toString())
                } else {
                    Toast.makeText(
                        this,
                        "Please enter username and password",
                        Toast.LENGTH_LONG
                    ).show()
                }

        }
    }

    private fun login(username: String, password: String) {
            viewModel.login(username, password)
    }

    private fun plantTimber() {
        Timber.plant(LoggingTree(resources.getString(R.string.app_name)))
        Timber.d("Timber is planted")
    }
}
