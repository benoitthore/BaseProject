package com.benoitthore.github

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.benoitthore.base.R
import com.benoitthore.github.ui.GithubFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_view, SOnOffFragment())
                .commit()
    }
}

class SOnOffFragment : Fragment(R.layout.sonoff) {

    private val client = OkHttpClient.Builder().apply { }.build()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.on_off_button)
                .setOnClickListener {
                    doRequest()
                }
    }

    private var job: Job? = null
    private fun doRequest(url: String = "http://192.168.1.144/?m=1&o=1") {
        if (job?.isActive == true) {
            return
        }
        job = GlobalScope.launch(Dispatchers.IO) {
            runCatching {
                val request = Request.Builder().apply {
                    url(url)
                }.build()
                val response = client.newCall(request).execute()
//                {t}{t}ON
//                {t}{t}OFF
                Unit
            }.exceptionOrNull()?.let {
                throw it
            }
        }
    }
}