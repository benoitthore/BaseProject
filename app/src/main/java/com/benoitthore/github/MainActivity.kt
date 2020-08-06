package com.benoitthore.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.base.R
import com.benoitthore.github.ui.GithubFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_view, GithubFragment())
                .commit()
    }
}