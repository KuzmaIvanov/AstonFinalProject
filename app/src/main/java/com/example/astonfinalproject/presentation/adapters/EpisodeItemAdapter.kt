package com.example.astonfinalproject.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.databinding.EpisodeItemBinding
import com.example.astonfinalproject.domain.entities.Episode

class EpisodeItemAdapter(
    var episodes: List<Episode>,
    val onItemClickAction: (Int) -> Unit
): RecyclerView.Adapter<EpisodeItemAdapter.EpisodeItemViewHolder>(), View.OnClickListener {

    class EpisodeItemViewHolder(
        val binding: EpisodeItemBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EpisodeItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return EpisodeItemViewHolder(binding)
    }

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: EpisodeItemViewHolder, position: Int) {
        val episode = episodes[position]
        with(holder.binding) {
            holder.itemView.tag = episode
            episodeNameTextView.text = episode.name
            episodeEpisodeTextView.text = episode.episode
            episodeAirDateTextView.text = episode.airDate
        }
    }

    override fun onClick(v: View) {
        onItemClickAction((v.tag as Episode).id)
    }
}