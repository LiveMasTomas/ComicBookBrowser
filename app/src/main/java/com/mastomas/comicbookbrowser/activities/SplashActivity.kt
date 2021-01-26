package com.mastomas.comicbookbrowser.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mastomas.comicbookbrowser.databinding.ActivitySplashBinding
import com.mastomas.comicbookbrowser.util.viewBinding

class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //App is too small for anything to take to spin up. so simulating by having this post delayed
        binding.root.postDelayed({ MainActivity.navigateTo(this) }, 750)
    }
}