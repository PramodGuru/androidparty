package com.pdguru.androidparty.servers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdguru.androidparty.R
import com.pdguru.androidparty.model.ServerLocations
import kotlinx.android.synthetic.main.activity_servers.view.*
import kotlinx.android.synthetic.main.item_server.view.*

class ServerAdapter(private val servers: List<ServerLocations>) : RecyclerView.Adapter<ServerAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_server, parent, false)

        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_country.text = servers[position].name
        holder.itemView.tv_distance.text = "${servers[position].distance} km"
    }

    override fun getItemCount(): Int {
        return servers.size
    }

}
