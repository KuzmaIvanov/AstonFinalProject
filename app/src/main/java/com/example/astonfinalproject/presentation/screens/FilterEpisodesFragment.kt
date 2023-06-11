package com.example.astonfinalproject.presentation.screens

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFilterEpisodesBinding
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.viewmodels.NetworkViewModel
import javax.inject.Inject

class FilterEpisodesFragment: BaseFragment<FragmentFilterEpisodesBinding>(R.layout.fragment_filter_episodes) {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun createBinding(view: View): FragmentFilterEpisodesBinding {
        return FragmentFilterEpisodesBinding.bind(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                networkViewModel.filterEpisodes(
                    name = binding.filterEpisodesNameEditText.text.toString(),
                    episode = binding.filterEpisodesEpisodeEditText.text.toString()
                )
                parentFragmentManager.popBackStack()
                true
            }
            else -> false
        }
    }
}