package com.mastomas.comicbookbrowser.fragments

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mastomas.comicbookbrowser.R
import com.mastomas.comicbookbrowser.R.layout
import com.mastomas.comicbookbrowser.adapters.ComicAdapter
import com.mastomas.comicbookbrowser.adapters.CustomItemDec
import com.mastomas.comicbookbrowser.adapters.PagingLoadStateAdapter
import com.mastomas.comicbookbrowser.databinding.FragmentCharacterDetailBinding
import com.mastomas.comicbookbrowser.util.viewBinding
import com.mastomas.comicbookbrowser.viewmodel.CharacterDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CharacterDetailFragment : Fragment() {

    //region Variables
    private val viewModel: CharacterDetailViewModel by viewModel()
    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    private val args: CharacterDetailFragmentArgs by navArgs()

    //listener so we know why the image loading failed
    private val glideListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            Timber.e(e, "Glide Failed")
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ) = false
    }
    //endregion

    //region Override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout.fragment_character_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        val adapter = ComicAdapter {
            goToComicWebView(it)
        }.also {
            setupUI(it)
        }
        viewModel.comics.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getComicsForCharacter(args.characterId.toString())
    }
    //endregion

    //region Private
    private fun goToComicWebView(pair: Pair<String?, Uri?>?) {
        if (pair == null) return
        pair.second?.let {
            val destination =
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToComicWebViewFragment(
                    comicTitle = pair.first,
                    comicUrl = it.toString()
                )
            findNavController().navigate(destination)
        }
    }

    private fun setupToolbar() {
        with(binding.toolBar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            title = args.characterName.orEmpty()
        }
    }

    private fun setupUI(adapter: ComicAdapter) {
        with(binding) {
            Glide.with(this@CharacterDetailFragment)
                .load(Uri.parse(args.imageUri.orEmpty()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.color.light_grey)
                .listener(glideListener)
                .override(Target.SIZE_ORIGINAL)
                .into(charImage)

            characterDescription.addCharacterDescription(args.characterDesc)

            errorView.retryButton.setOnClickListener {
                adapter.retry()
            }

            emptyView.retryButton.setOnClickListener {
                adapter.retry()
            }

            adapter.addLoadStateListener {
                //Loading State
                loadingView.root.isVisible = it.refresh is Loading

                //Error State
                errorView.root.isVisible = it.refresh is Error

                //Empty State
                emptyView.root.isVisible = it.refresh is NotLoading && adapter.itemCount == 0
            }
        }

        with(binding.detailRV) {
            addItemDecoration(CustomItemDec())
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(adapter::retry),
                footer = PagingLoadStateAdapter(adapter::retry)
            )
        }
    }
    //endregion
}