package com.mastomas.comicbookbrowser.util

/**
 * Quick extension to check nullable ints
 */
fun Int?.isGreaterThan(value: Int): Boolean =
    this != null && this > value