package com.benoitthore.words.ui

import android.content.Context
import android.graphics.Color
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
import com.benoitthore.words.features.utils.delegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.views.backgroundColor
import splitties.views.dsl.core.button
import splitties.views.dsl.core.editText

//interface IAddView {
//
//}

class AddWordUI(context: Context,

        // TODO Find something cleaner
                val data: Data = Data("", ""),
                val strings: Strings = Strings("Ok")
) {

    data class Data(val wordA: String, val wordB: String)
    data class Strings(val okButton: String)


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
    }.withTag("okButton")


    private val eViewGroup = context.eViewGroup {

        backgroundColor = Color.WHITE

        val inputLayout = listOf(inputA, inputB).laid()
                .equallySized(rightCenter, 32.dp)
                .snugged()
                .paddedHorizontally(32.dp)

        val confirmationLayout = okButton.laid()

        listOf(inputLayout, confirmationLayout)
                .stackedBottomCenter(16.dp)
                .snugged()
                .arranged(center)
    }


    fun onOkClicked(block: () -> Unit) {
        okButton.setOnClickListener { block() }
    }

    val view: View = eViewGroup


    ////////////////
    ////////////////
    ////////////////

    var wordA: String by inputA.delegate()
    var wordB: String by inputB.delegate()
}