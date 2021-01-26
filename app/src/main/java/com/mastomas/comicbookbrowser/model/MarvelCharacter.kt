package com.mastomas.comicbookbrowser.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Uri,
    val legalText: String?
) : Parcelable