package com.example.astonfinalproject.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.astonfinalproject.databinding.CharacterItemBinding
import com.example.astonfinalproject.domain.entities.Character

class CharacterItemAdapter(
    var characters: List<Character>,
    val onItemClickAction: (Int) -> Unit
) : RecyclerView.Adapter<CharacterItemAdapter.CharacterItemViewHolder>(), View.OnClickListener {

    class CharacterItemViewHolder(
        val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CharacterItemViewHolder(binding)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        val character = characters[position]
        with(holder.binding) {
            holder.itemView.tag = character
            characterImageView.load(character.imageUrl)
            characterNameTextView.text = character.name
            characterSpeciesTextView.text = character.species
            characterStatusTextView.text = character.status
            characterGenderTextView.text = character.gender
        }
    }
    override fun onClick(v: View) {
        onItemClickAction((v.tag as Character).id)
    }
}