package com.pdguru.androidparty.servers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdguru.androidparty.R
import com.pdguru.androidparty.databinding.ActivityServersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class ServerActivity : AppCompatActivity() {
    private val viewModel by viewModel<ServerActivityViewModel>()
    private lateinit var binding: ActivityServersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        viewModel.getServerList()
        observeUiState()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeUiState() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_servers)
        viewModel.state.observe(this) { state ->
            state.serverList?.let {
                recyclerView.apply {
                    adapter = ServerAdapter(it)
                    recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
                    val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
                    itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
                }
            }
            if (state.logout) exitProcess(0)
        }
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_servers)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
