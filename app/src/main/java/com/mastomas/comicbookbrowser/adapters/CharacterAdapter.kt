package com.mastomas.comicbookbrowser.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mastomas.comicbookbrowser.R
import com.mastomas.comicbookbrowser.adapters.CharacterAdapter.CharacterViewHolder
import com.mastomas.comicbookbrowser.databinding.ViewItemCardViewBinding
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import timber.log.Timber

class CharacterAdapter(private val clickListener: ((MarvelCharacter?) -> Unit)?) :
    PagingDataAdapter<MarvelCharacter, CharacterViewHolder>(CHAR_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ViewItemCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    inner class CharacterViewHolder(private val binding: ViewItemCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

        private var currentChar: MarvelCharacter? = null

        init {
            binding.root.setOnClickListener {
                clickListener?.invoke(currentChar)
            }
        }

        fun bind(char: MarvelCharacter, position: Int) {
            currentChar = char
            with(binding) {
                Glide.with(itemView)
                    .load(char.thumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.color.light_grey)
                    .centerCrop()
                    .listener(glideListener)
                    .into(itemCardImage)

                itemCardName.text = char.name
                itemCardImage.transitionName = "transitionImage$position"
            }
        }

    }

    companion object {
        private val CHAR_DIFF = object : DiffUtil.ItemCallback<MarvelCharacter>() {
            override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter) =
                oldItem == newItem
        }
    }
}
