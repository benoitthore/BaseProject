package com.benoitthore.words

import android.content.Context
import android.view.Gravity.CENTER
import android.view.View
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.EEmptyLayout
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import com.benoitthore.enamel.layout.android.laidIn
import com.benoitthore.enamel.layout.android.withTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.views.dsl.core.button
import splitties.views.dsl.core.editText

//interface IAddView {
//
//}

class AddWordUI(context: Context,
                val data: Data = Data("", ""),
                val strings: Strings = Strings("Ok")
) {

    data class Data(val wordA: String, val wordB: String)
    data class Strings(val okButton: String)


    private val eViewGroup = context.eViewGroup { EEmptyLayout }

    private val inputA = context.editText {
        setText(data.wordA)
        gravity = CENTER
    }.withTag("inputA")

    private val inputB = context.editText {
        setText(data.wordB)
        gravity = CENTER
    }.withTag("inputB")

    private val okButton = context.button {
        text = strings.okButton

        var canClick = true
        setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if (canClick) {
                    canClick = false
                    hide()
                    delay(1100L)
                    show()
                    canClick = true
                }
            }
        }
    }.withTag("okButton")


    private val layout: ELayout = run {
        val inputLayout = listOf(inputA, inputB).laidIn(eViewGroup)
                .equallySized(rightCenter,32.dp)
                .snugged()
                .paddedHorizontally(32.dp)

        val confirmationLayout = okButton.laidIn(eViewGroup)

        listOf(inputLayout, confirmationLayout)
                .stackedBottomCenter(16.dp)
                .snugged()
                .arranged(center)
    }

    private val hiddenLayout = run {
        listOf(inputA, inputB, okButton).laidIn(eViewGroup)
                .stackedRightCenter()
                .snugged()
                .sized(100,100)
                .arranged(topLeft)
    }

    fun hide() {
        eViewGroup.transitionTo(hiddenLayout)
    }

    fun show() {
        eViewGroup.transitionTo(layout)
    }

    val view: View = eViewGroup
}