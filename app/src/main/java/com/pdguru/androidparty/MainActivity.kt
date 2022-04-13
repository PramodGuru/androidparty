package com.pdguru.androidparty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.state.observe(this, Observer { state ->
            if (state.message != null) {
                showToast(state.message)
            }
            showLoading(state.isProcessing)
            if (state.goNext) {
                callIntent()
            }
        })
    }

    private fun callIntent() {
        startActivity(Intent(this, ServerActivity::class.java))
    }

    private fun showLoading(processing: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)

        progressBar.visibility = if (processing) View.VISIBLE else View.GONE
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
                login(username.text.toString(), password.text.toString())
            } else {
                showToast("Please enter username and password")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
