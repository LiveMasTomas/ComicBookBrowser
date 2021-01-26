package com.mastomas.comicbookbrowser.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelComic(
    val id: Int,
    val title: String,
    val thumbnail: Uri,
    val infoUrl: Uri,
    val legalText: String?
) : Parcelable