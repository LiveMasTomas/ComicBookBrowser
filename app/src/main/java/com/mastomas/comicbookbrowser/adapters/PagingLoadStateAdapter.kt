package com.mastomas.comicbookbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mastomas.comicbookbrowser.adapters.PagingLoadStateAdapter.LoadStateViewHolder
import com.mastomas.comicbookbrowser.databinding.ViewLoadStateFooterHeaderBinding

class PagingLoadStateAdapter(private val retry: (() -> Unit)?) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ViewLoadStateFooterHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)


    inner class LoadStateViewHolder(private val binding: ViewLoadStateFooterHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.errorButton.setOnClickListener { retry?.invoke() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loadingView.root.isVisible = loadState is LoadState.Loading
                errorText.isVisible = loadState is LoadState.Error
                errorButton.isVisible = loadState is LoadState.Error
            }
        }
    }
}