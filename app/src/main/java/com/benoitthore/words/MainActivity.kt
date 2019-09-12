package com.benoitthore.words

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.size
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.words.features.add.AddWordFragment
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
import kotlin.math.max


class MainActivity : AppCompatActivity() {

    private lateinit var frame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        frame = frameLayout {
            // TODO Refactor to use ELayout instead
            id = View.generateViewId()
            backgroundColor = Color.RED
        }

        setContentView(frame)

        supportFragmentManager.beginTransaction()
                .replace(frame.id, AddWordFragment())
                .addToBackStack(AddWordFragment::class.java.name)
                .commit()
    }
}

class EFrameLayout(override val childLayouts: List<ELayout>) : ELayout {

    override fun arrange(frame: ERect) {
        childLayouts.forEach { it.arrange(frame) }
    }

    override fun size(toFit: ESize): ESize {
        var w = 0f
        var h = 0f
        childLayouts.forEach {
            val s = it.size(toFit)
            w = max(w, s.width)
            h = max(h, s.height)
        }

        return w size h
    }
}
