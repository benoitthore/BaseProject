package com.benoitthore.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.geometry.alignement.EAlignment.center
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.layout.dsl.stackedBottomCenter
import com.benoitthore.enamel.layout.android.EViewGroup
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import splitties.views.dsl.core.button
import splitties.views.dsl.core.textView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}