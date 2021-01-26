package com.mastomas.comicbookbrowser.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.mastomas.comicbookbrowser.databinding.ViewCharacterDetailBinding

class CharacterDescriptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding: ViewCharacterDetailBinding =
        ViewCharacterDetailBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Handling the case where the character's description can be null or empty
     */
    fun addCharacterDescription(desc: String?) {
        with(binding) {
            if (desc.isNullOrBlank()) {
                charDesc.isVisible = false
                charDivider.isVisible = false
            } else {
                charDesc.text = desc
            }
        }
    }
}