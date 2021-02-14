package com.mastomas.comicbookbrowser.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mastomas.comicbookbrowser.R
import com.mastomas.comicbookbrowser.databinding.FragmentComicWebviewBinding
import com.mastomas.comicbookbrowser.util.viewBinding
import timber.log.Timber

class ComicWebViewFragment : Fragment() {

    //region Variables
    private val binding: FragmentComicWebviewBinding by viewBinding(FragmentComicWebviewBinding::bind)
    private val args: ComicWebViewFragmentArgs by navArgs()
    //endregion

    //region Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_comic_webview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupWebView()
    }
    //endregion

    //region Private
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val client = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                try {
                    binding.loadingView.root.isVisible = true
                    binding.webView.isVisible = false
                } catch (t: Throwable) {
                    Timber.e(t, "webView error - started")
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    binding.loadingView.root.isVisible = false
                    binding.webView.isVisible = true
                } catch (t: Throwable) {
                    Timber.e(t, "webView error - finished")
                }
            }
        }
        with(binding.webView) {
            settings.apply {
                //need to enable otherwise website looks bad and confusing
                javaScriptEnabled = true
            }
            loadUrl(args.comicUrl)
            webViewClient = client
        }
    }

    private fun setupToolbar() {
        with(binding.toolBar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            title = args.comicTitle.orEmpty()
        }
    }
    //endregion
}