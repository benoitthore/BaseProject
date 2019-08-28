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
import splitties.views.dsl.core.button
import splitties.views.dsl.core.textView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val v = eViewGroup {

            //            backgroundColor = Color.RED

            val tv1 = context.textView {

                text = "text1"

            }.eLayoutRef()

            val tv2 = context.button {

                text = "text2"

            }.eLayoutRef()


            listOf(tv1, tv2)
                    .stackedBottomCenter(32.dp)
                    .arranged(center)

        }


        setContentView(v)

    }
}


fun Context.eViewGroup(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
        this
).apply {
    layout = block()
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()