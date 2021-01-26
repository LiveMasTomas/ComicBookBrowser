package com.mastomas.comicbookbrowser.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.mastomas.comicbookbrowser.adapters.ComicAdapter.ComicViewHolder
import com.mastomas.comicbookbrowser.databinding.ViewItemCardViewBinding
import com.mastomas.comicbookbrowser.model.MarvelComic
import timber.log.Timber

class ComicAdapter(private val clickListener: ((Pair<String?, Uri?>?) -> Unit)?) :
    PagingDataAdapter<MarvelComic, ComicViewHolder>(COMIC_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = ViewItemCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ComicViewHolder(private val binding: ViewItemCardViewBinding) :
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

        private var currentComic: Pair<String?, Uri?>? = null

        init {
            binding.root.setOnClickListener {
                clickListener?.invoke(currentComic)
            }
        }

        fun bind(comic: MarvelComic) {
            currentComic = comic.title to comic.infoUrl
            with(binding) {
                Glide.with(itemView)
                    .load(comic.thumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.color.light_grey)
                    .centerCrop()
                    .listener(glideListener)
                    .into(itemCardImage)

                itemCardName.text = comic.title
            }
        }
    }

    companion object {
        private val COMIC_DIFF = object : DiffUtil.ItemCallback<MarvelComic>() {
            override fun areItemsTheSame(oldItem: MarvelComic, newItem: MarvelComic) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MarvelComic, newItem: MarvelComic) =
                oldItem == newItem
        }
    }
}