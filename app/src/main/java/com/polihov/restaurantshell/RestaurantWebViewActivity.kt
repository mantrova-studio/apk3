package com.polihov.restaurantshell

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.polihov.restaurantshell.databinding.ActivityRestaurantWebviewBinding

class RestaurantWebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantWebviewBinding

    companion object {
        const val EXTRA_ID = "restaurant_id"
        const val EXTRA_NAME = "restaurant_name"
        const val EXTRA_URL = "restaurant_url"

        private const val PREFS_NAME = "restaurant_app_prefs"
        private const val KEY_LAST_RESTAURANT_ID = "last_restaurant_id"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantName = intent.getStringExtra(EXTRA_NAME).orEmpty().ifBlank { "Ресторан" }
        val restaurantUrl = intent.getStringExtra(EXTRA_URL).orEmpty().ifBlank { "https://example.com" }

        binding.textRestaurantTitle.text = restaurantName

        binding.textChangeRestaurant.setOnClickListener {
            clearLastRestaurant()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.webView.reload()
        }

        binding.swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
            binding.webView.scrollY > 0
        }

        configureWebView()
        binding.webView.loadUrl(restaurantUrl)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    private fun clearLastRestaurant() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit()
            .remove(KEY_LAST_RESTAURANT_ID)
            .apply()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadsImagesAutomatically = true
            cacheMode = WebSettings.LOAD_DEFAULT
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(false)
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            allowFileAccess = false
            allowContentAccess = true
        }

        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString().orEmpty()
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false
                }

                return try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    true
                } catch (_: ActivityNotFoundException) {
                    Toast.makeText(
                        this@RestaurantWebViewActivity,
                        "Не удалось открыть ссылку",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = android.view.View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = android.view.View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
                super.onPageFinished(view, url)
            }
        }
    }

    override fun onDestroy() {
        binding.webView.stopLoading()
        binding.swipeRefreshLayout.isRefreshing = false
        binding.webView.destroy()
        super.onDestroy()
    }
}
