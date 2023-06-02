package com.example.astonfinalproject.presentation.screens.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharactersBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.CharacterItemAdapter
import com.example.astonfinalproject.presentation.pagination.RecyclerViewGridLayoutScrollListener
import com.example.astonfinalproject.presentation.viewmodels.characters.CharactersViewModel
import kotlinx.coroutines.launch

class CharactersFragment : BaseFragment<FragmentCharactersBinding, CharactersViewModel>(
    R.layout.fragment_characters,
    CharactersViewModel::class.java
) {

    override fun createBinding(view: View): FragmentCharactersBinding {
        return FragmentCharactersBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CharacterItemAdapter(
            characters = listOf(),
            onItemClickAction = { characterId ->
                val detailsFragment = CharacterDetailsFragment().apply {
                    this.arguments =
                        bundleOf(CharacterDetailsFragment.CHARACTER_ID_KEY to characterId)
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.charactersRecyclerView.adapter = adapter
        binding.charactersRecyclerView.addOnScrollListener(RecyclerViewGridLayoutScrollListener{
            viewModel.loadNextCharacters()
        })
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterList
                    .collect {
                        if(it.isSuccess) {
                            adapter.characters = it.getOrThrow()
                            adapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(requireContext(), "Failed to load!",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container_view,
                            CharactersSearchFragment()
                                .apply {
                                    arguments = bundleOf(
                                        CharactersSearchFragment.CHARACTERS_SEARCH_NAME_KEY to query
                                    )
                                }
                        )
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.search_view_query_empty_toast,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_bar -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, FilterCharactersFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }

            else -> false
        }
    }
}