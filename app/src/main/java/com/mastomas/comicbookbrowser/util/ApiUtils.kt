package com.mastomas.comicbookbrowser.util

import com.mastomas.comicbookbrowser.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

object ApiUtils {
    //region Types of OrderBy
    const val NAME = "name"
    const val MODIFIED = "modified"
    const val REVERSE_NAME = "-name"
    const val REVERSE_MODIFIED = "-modified"
    const val TITLE = "title"
    const val REVERSE_TITLE = "-title"
    //endregion

    fun getMd5String(timestamp: String): String {
        val input = "$timestamp${BuildConfig.MARVEL_SECRET_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}"
        return BigInteger(
            1,
            MessageDigest.getInstance("MD5").digest(input.toByteArray())
        ).toString(16).padStart(32, '0')
    }
}