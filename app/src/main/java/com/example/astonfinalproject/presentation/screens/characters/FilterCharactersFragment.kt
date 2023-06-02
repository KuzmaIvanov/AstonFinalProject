package com.example.astonfinalproject.presentation.screens.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFilterCharactersBinding

class FilterCharactersFragment : Fragment() {

    private var _binding: FragmentFilterCharactersBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                val charactersSearchFragment = CharactersSearchFragment().apply {
                    arguments = getBundleForCharacterSearchFragment()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, charactersSearchFragment)
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }

    private fun getBundleForCharacterSearchFragment(): Bundle {
        return bundleOf(
            CharactersSearchFragment.CHARACTERS_SEARCH_NAME_KEY to binding.filterCharactersNameEditText.text.toString(),
            CharactersSearchFragment.CHARACTERS_SEARCH_STATUS_KEY to getStatusFromCheckedChip(),
            CharactersSearchFragment.CHARACTERS_SEARCH_SPECIES_KEY to binding.filterCharactersSpeciesEditText.text.toString(),
            CharactersSearchFragment.CHARACTERS_SEARCH_TYPE_KEY to binding.filterCharactersTypeEditText.text.toString(),
            CharactersSearchFragment.CHARACTERS_SEARCH_GENDER_KEY to getGenderFromCheckedChip()
        )
    }

    private fun getStatusFromCheckedChip(): String? {
        return when(binding.filterCharactersStatusChipGroup.checkedChipId) {
            R.id.filter_characters_status_alive_chip -> "alive"
            R.id.filter_characters_status_dead_chip -> "dead"
            R.id.filter_characters_status_unknown_chip -> "unknown"
            else -> null
        }
    }

    private fun getGenderFromCheckedChip(): String? {
        return when (binding.filterCharactersGenderChipGroup.checkedChipId) {
            R.id.filter_characters_gender_female_chip -> "female"
            R.id.filter_characters_gender_male_chip -> "male"
            R.id.filter_characters_gender_genderless_chip -> "genderless"
            R.id.filter_characters_gender_unknown_chip -> "unknown"
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}