package com.benoitthore.words

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.words.home.AddWordUI


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = AddWordUI(
                this,
                data = AddWordUI.Data("word1", "word2")
        )

        ui.apply { show() }

        setContentView(ui.view)

    }
}