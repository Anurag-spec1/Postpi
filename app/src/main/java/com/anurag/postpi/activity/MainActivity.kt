package com.anurag.postpi.activity

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.anurag.postpi.R
import com.anurag.postpi.adapter.RequestPagerAdapter
import com.anurag.postpi.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: RequestPagerAdapter

    private var selectedMethod = "GET"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pagerAdapter = RequestPagerAdapter(this)
        binding.requestPager.adapter = pagerAdapter

        TabLayoutMediator(binding.requestTabs, binding.requestPager) { tab, pos ->
            tab.text = when (pos) {
                0 -> "Params"
                1 -> "Headers"
                2 -> "Body"
                3 -> "Auth"
                else -> ""
            }
        }.attach()

        setupMethodSelector()

        binding.sendReq.setOnClickListener {
            sendRequest()
        }
    }

    private fun setupMethodSelector() {
        binding.methodSelector.setOnClickListener {
            val popup = PopupMenu(this, binding.methodSelector)
            popup.menuInflater.inflate(R.menu.menu_http_methods, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                selectedMethod = item.title.toString()
                binding.methodSelector.text = selectedMethod

                binding.methodSelector.setTextColor(
                    when (selectedMethod) {
                        "GET" -> getColor(carbon.R.color.carbon_green_500)
                        "POST" -> getColor(carbon.R.color.carbon_blue_500)
                        "PUT" -> getColor(carbon.R.color.carbon_orange_500)
                        "DELETE" -> getColor(carbon.R.color.carbon_red_500)
                        else -> getColor(R.color.white)
                    }
                )
                true
            }
            popup.show()
        }
    }

    private fun sendRequest() {

        val url = binding.etApi.text.toString().trim()
        if (url.isEmpty()) {
            toast("Enter URL")
            return
        }

        val headers = pagerAdapter.headersFragment.getEnabledHeaders()
        val requestBody = pagerAdapter.bodyFragment.getRequestBody(selectedMethod)

        val finalBody = when (selectedMethod) {
            "POST", "PUT", "PATCH" ->
                requestBody ?: "".toRequestBody("text/plain".toMediaType())
            else -> null
        }

        val requestBuilder = Request.Builder()
            .url(url)
            .method(selectedMethod, finalBody)

        headers.forEach {
            requestBuilder.addHeader(it.key.trim(), it.value.trim())
        }

        val request = requestBuilder.build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    showResponse(0, e.message ?: "Unknown error")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string().orEmpty()
                runOnUiThread {
                    showResponse(response.code, body)
                }
            }
        })
    }

    private fun showResponse(code: Int, body: String) {
        AlertDialog.Builder(this)
            .setTitle("Response ($code)")
            .setMessage(body.ifEmpty { "No response body" })
            .setPositiveButton("OK", null)
            .show()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
