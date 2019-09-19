package com.benoitthore.words.ui

import android.content.Context
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.layout.dsl.stackedBottomCenter
import com.benoitthore.enamel.geometry.layout.dsl.stackedTopCenter
import com.benoitthore.enamel.layout.android.eViewGroup
import com.benoitthore.words.data.WordsData
import splitties.views.dsl.core.button
import splitties.views.dsl.core.textView

class ListWordUI(context: Context) {

    private val tv = context.textView { text = "Working" }
    private val addButton = context.button { text = "Add" }

    val eViewGroup = context.eViewGroup {

        listOf(addButton, tv)
                .laid()
                .stackedTopCenter()
                .arranged(center)
    }

    var words: List<WordsData> = emptyList()
        set(value) {
            field = value
            tv.text = words.toString()
        }

    val view get() = eViewGroup


    fun onAddClicked(block: () -> Unit) {
        addButton.setOnClickListener { block() }
    }
}