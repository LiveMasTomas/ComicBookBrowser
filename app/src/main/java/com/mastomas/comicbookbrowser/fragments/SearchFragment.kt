package com.mastomas.comicbookbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mastomas.comicbookbrowser.R
import com.mastomas.comicbookbrowser.adapters.CharacterAdapter
import com.mastomas.comicbookbrowser.adapters.CustomItemDec
import com.mastomas.comicbookbrowser.adapters.PagingLoadStateAdapter
import com.mastomas.comicbookbrowser.databinding.FragmentSearchBinding
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.util.hideKeyboard
import com.mastomas.comicbookbrowser.util.viewBinding
import com.mastomas.comicbookbrowser.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    //region Variables
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: CharacterViewModel by viewModel()
    //endregion

    //region Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        val adapter = CharacterAdapter { char ->
            char?.let { goToCharacterDetail(it) }
        }.also {
            setupUI(it)
        }
        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
    //endregion

    //region Private
    private fun goToCharacterDetail(char: MarvelCharacter) {
        val directions = SearchFragmentDirections.actionSearchFragmentToCharacterDetailFragment(
            characterName = char.name,
            characterId = char.id,
            characterDesc = char.description,
            imageUri = char.thumbnail.toString()
        )
        findNavController().navigate(directions)
    }

    private fun setupUI(adapter: CharacterAdapter) {
        with(binding.searchRV) {
            addItemDecoration(CustomItemDec())
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(adapter::retry),
                footer = PagingLoadStateAdapter(adapter::retry)
            )
        }
        with(binding) {
            swipeContainer.setOnRefreshListener {
                adapter.refresh()
            }
            errorView.retryButton.setOnClickListener {
                adapter.retry()
            }
            emptyView.retryButton.setOnClickListener {
                adapter.retry()
            }
            adapter.addLoadStateListener {
                //Loading State
                loadingView.root.isVisible = it.refresh is Loading && adapter.itemCount == 0
                swipeContainer.isRefreshing = it.refresh is Loading

                //Error State
                errorView.root.isVisible = it.refresh is Error

                //Empty State
                emptyView.root.isVisible = it.refresh is NotLoading && adapter.itemCount == 0
            }
        }
    }

    private fun setupAppBar() {
        with(binding) {
            toolBar.setNavigationOnClickListener {
                hideKeyboard()
                findNavController().navigateUp()
            }
            searchView.setOnEditorActionListener { v, actionId, _ ->
                var handled = false
                if (actionId == IME_ACTION_SEARCH) {
                    viewModel.searchCharacters(v.text.toString())
                    hideKeyboard()
                    handled = true
                }
                return@setOnEditorActionListener handled
            }
        }
    }
    //endregion
}