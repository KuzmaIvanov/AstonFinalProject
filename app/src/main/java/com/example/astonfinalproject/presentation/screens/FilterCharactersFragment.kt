package com.example.astonfinalproject.presentation.screens

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFilterCharactersBinding
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.viewmodels.NetworkViewModel
import javax.inject.Inject

class FilterCharactersFragment : BaseFragment<FragmentFilterCharactersBinding>(R.layout.fragment_filter_characters) {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun createBinding(view: View): FragmentFilterCharactersBinding {
        return FragmentFilterCharactersBinding.bind(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                networkViewModel.filterCharacters(
                    name = binding.filterCharactersNameEditText.text.toString(),
                    status  = getStatusFromCheckedChip(),
                    species= binding.filterCharactersSpeciesEditText.text.toString(),
                    type = binding.filterCharactersTypeEditText.text.toString(),
                    gender = getGenderFromCheckedChip()
                )
                parentFragmentManager.popBackStack()
                true
            }
            else -> false
        }
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
        return when(binding.filterCharactersGenderChipGroup.checkedChipId) {
            R.id.filter_characters_gender_female_chip -> "female"
            R.id.filter_characters_gender_male_chip -> "male"
            R.id.filter_characters_gender_genderless_chip -> "genderless"
            R.id.filter_characters_gender_unknown_chip -> "unknown"
            else -> null
        }
    }
}