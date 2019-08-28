package com.benoitthore.words.home

import android.content.Context
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
import splitties.views.dsl.core.textView

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

    private val inputA = context.textView {
        setText(data.wordA)
    }.withTag("inputA")

    private val inputB = context.textView {
        setText(data.wordB)
    }.withTag("inputB")

    private val okButton = context.button {
        text = strings.okButton

        var canClick = true
        setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if (canClick) {
                    canClick = false
                    hide()
                    delay(1500L)
                    show()
                    canClick = true
                }
            }
        }
    }.withTag("okButton")


    private val layout: ELayout = run {
        val inputLayout = listOf(inputA, inputB).laidIn(eViewGroup)
                .stackedRightCenter(8.dp)
                .snugged()

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
                .sized(0,0)
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