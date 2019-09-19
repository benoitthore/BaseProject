package com.benoitthore.words

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.benoitthore.words.features.show.ListWordsFragment
import splitties.views.backgroundColor
import splitties.views.dsl.core.*


class MainActivity : AppCompatActivity() {

    val frame: FrameLayout by lazy {
        frameLayout {
            // TODO Refactor to use ELayout instead
            id = View.generateViewId()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(frame)

        supportFragmentManager.beginTransaction()
                .replace(frame.id, ListWordsFragment())
                .commit()
    }

    inline fun <reified T : Fragment> loadFragment() {
        val clazz = T::class.java
        supportFragmentManager.beginTransaction()
                .replace(frame.id, clazz.newInstance())
                .addToBackStack(T::class.java.name)
                .commit()
    }
}
