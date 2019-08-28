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

        val v = eViewGroup {

            //            backgroundColor = Color.RED

            val tv1 = context.textView {

                text = "text1"

            }.eLayoutRef()


            val tv2 = context.button {

                setOnClickListener {
                    text = ((text.toString().toIntOrNull() ?: 0) + 1).toString()
                }
                text = "text2"

            }.eLayoutRef()


            listOf(tv1, tv2)
                    .stackedBottomCenter(16.dp)
                    .arranged(center)

        }


        setContentView(v)

    }
}