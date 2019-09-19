package com.benoitthore.words

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.words.features.show.ListWordsFragment
import splitties.views.backgroundColor
import splitties.views.dsl.core.*


class MainActivity : AppCompatActivity() {

    private lateinit var frame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        frame = frameLayout {
            // TODO Refactor to use ELayout instead
            id = View.generateViewId()
        }

        setContentView(frame)

        supportFragmentManager.beginTransaction()
                .replace(frame.id, ListWordsFragment())
                .addToBackStack(ListWordsFragment::class.java.name)
                .commit()
    }
}
