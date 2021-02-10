package com.mastomas.comicbookbrowser.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mastomas.comicbookbrowser.databinding.ActivityMainBinding
import com.mastomas.comicbookbrowser.util.viewBinding
import com.mastomas.comicbookbrowser.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    //region Variables
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel<CharacterViewModel>()
    //endregion

    //region Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setContentView(binding.root)
    }
    //endregion

    //region Comp Obj
    companion object {
        fun navigateTo(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            activity.finish()
        }
    }
    //endregion
}