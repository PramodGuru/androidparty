package com.pdguru.androidparty

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class ServerActivity : AppCompatActivity() {
//    private val viewModel by viewModel<ServerActivityViewModel>()
//    private lateinit var binding: ActivityServersBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_servers)
    }

//    private fun setupDataBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_servers)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
//        binding.executePendingBindings()
//    }
}
