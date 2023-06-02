package com.example.astonfinalproject.presentation.screens.episodes

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
import com.example.astonfinalproject.databinding.FragmentFilterEpisodesBinding

class FilterEpisodesFragment: Fragment() {

    private var _binding: FragmentFilterEpisodesBinding? = null
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
        _binding = FragmentFilterEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                val episodesSearchFragment = EpisodesSearchFragment().apply {
                    arguments = getBundleForEpisodesSearchFragment()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, episodesSearchFragment)
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBundleForEpisodesSearchFragment(): Bundle {
        return bundleOf(
            EpisodesSearchFragment.EPISODES_SEARCH_NAME to binding.filterEpisodesNameEditText.text.toString(),
            EpisodesSearchFragment.EPISODES_SEARCH_EPISODE to binding.filterEpisodesEpisodeEditText.text.toString()
        )
    }
}