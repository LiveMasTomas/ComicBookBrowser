package com.mastomas.comicbookbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mastomas.comicbookbrowser.R
import com.mastomas.comicbookbrowser.databinding.FragmentMoreInfoBinding
import com.mastomas.comicbookbrowser.util.viewBinding

class MoreInfoFragment : Fragment() {

    //region Variables
    private val binding: FragmentMoreInfoBinding by viewBinding(FragmentMoreInfoBinding::bind)
    //endregion

    //region Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_more_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
    //endregion
}