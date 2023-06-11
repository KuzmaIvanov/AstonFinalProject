package com.example.astonfinalproject.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.network.NetworkUiState
import com.example.astonfinalproject.databinding.FragmentCharactersBinding
import com.example.astonfinalproject.domain.entities.CharacterList
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.viewmodels.NetworkViewModel
import com.example.astonfinalproject.presentation.adapters.CharacterItemAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersFragment : BaseFragment<FragmentCharactersBinding>(R.layout.fragment_characters) {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun createBinding(view: View): FragmentCharactersBinding {
        return FragmentCharactersBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CharacterItemAdapter(listOf())
        binding.charactersRecyclerView.adapter = adapter
        binding.charactersRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            networkViewModel.characterListUiState
                .flowWithLifecycle(lifecycle)
                .collect { uiState ->
                    when (uiState) {
                        is NetworkUiState.Success<*> -> {
                            adapter.characters = (uiState.data as CharacterList).characters
                            adapter.notifyDataSetChanged()
                        }

                        is NetworkUiState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Error: ${uiState.message.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
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
                if(!query.isNullOrEmpty()) {
                    networkViewModel.filterCharacters(name = query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
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