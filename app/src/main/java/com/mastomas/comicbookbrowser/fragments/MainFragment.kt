package com.mastomas.comicbookbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.mastomas.comicbookbrowser.databinding.FragmentMainBinding
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.util.viewBinding
import com.mastomas.comicbookbrowser.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This is the first fragment that the user will see. Contains just a grid of heroes from the Marvel
 * endpoint, ordered from the latest modified category. Hoping this will give the user a new set
 * of characters to look at, periodically, when they launch the app.
 */
class MainFragment : Fragment() {

    //region Variables
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: CharacterViewModel by viewModel()
    //endregion

    //region Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        val adapter = CharacterAdapter { char ->
            char?.let { goToCharacterDetail(it) }
        }.also {
            setupUI(it)
        }
        viewModel.latestHeroes.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
    //endregion

    //region Private
    private fun goToCharacterDetail(char: MarvelCharacter) {
        val direction = MainFragmentDirections.actionMainFragmentToCharacterDetailFragment(
            characterId = char.id,
            characterDesc = char.description,
            characterName = char.name,
            imageUri = char.thumbnail.toString()
        )
        findNavController().navigate(direction)
    }

    private fun setupAppBar() {
        with(binding) {
            toolBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search -> {
                        val direction = MainFragmentDirections.actionMainFragmentToSearchFragment()
                        findNavController().navigate(direction)
                        true
                    }
                    R.id.moreInfo -> {
                        val direction = MainFragmentDirections.actionMainFragmentToMoreInfoFragment()
                        findNavController().navigate(direction)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupUI(adapter: CharacterAdapter) {
        with(binding.mainRV) {
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
            /**
             * @link https://developer.android.com/reference/kotlin/androidx/paging/PagingDataAdapter#addLoadStateListener(kotlin.Function1)
             */
            adapter.addLoadStateListener {
                //Loading State
                loadingView.root.isVisible = it.refresh is Loading
                swipeContainer.isRefreshing = it.refresh is Loading

                //Error State
                errorView.root.isVisible = it.refresh is Error

                //Empty State
                emptyView.root.isVisible = it.refresh is NotLoading && adapter.itemCount == 0
            }
        }
    }
    //endregion
}