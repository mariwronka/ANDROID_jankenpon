package com.mariwronka.jankenpon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mariwronka.jankenpon.data.source.local.PlayerData
import com.mariwronka.jankenpon.databinding.ViewRankingPlayerItemBinding

class RankingAdapter(players: List<PlayerData>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    private val players = players.toMutableList()

    class ViewHolder(private val binding: ViewRankingPlayerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playerData: PlayerData) {
            binding.textRankName.text = playerData.playerName
            binding.textRankPoints.text = playerData.points.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewRankingPlayerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size
}
