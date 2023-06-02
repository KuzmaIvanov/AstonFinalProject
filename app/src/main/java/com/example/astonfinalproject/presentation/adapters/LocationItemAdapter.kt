package com.example.astonfinalproject.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.databinding.LocationItemBinding
import com.example.astonfinalproject.domain.entities.Location

class LocationItemAdapter(
    var locations: List<Location>,
    val onItemClickAction: (Int) -> Unit
): RecyclerView.Adapter<LocationItemAdapter.LocationItemViewHolder>(), View.OnClickListener {

    class LocationItemViewHolder(
        val binding: LocationItemBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return LocationItemViewHolder(binding)
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: LocationItemViewHolder, position: Int) {
        val location = locations[position]
        with(holder.binding) {
            holder.itemView.tag = location
            locationNameTextView.text = location.name
            locationTypeTextView.text = location.type
            locationDimensionTextView.text = location.dimension
        }
    }

    override fun onClick(v: View) {
        val location = v.tag as Location
        onItemClickAction(location.id)
    }
}