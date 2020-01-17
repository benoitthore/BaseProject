package com.benoitthore.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.base.helloworld.HelloWorldFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_view, HelloWorldFragment())
                .commit()
    }
}