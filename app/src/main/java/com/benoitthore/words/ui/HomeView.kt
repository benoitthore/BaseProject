package com.benoitthore.words.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.dsl.arranged
import com.benoitthore.enamel.geometry.layout.dsl.justified
import com.benoitthore.enamel.geometry.layout.dsl.stackedRightCenter
import com.benoitthore.enamel.geometry.layout.dsl.stackedTopCenter
import com.benoitthore.enamel.layout.android.EViewGroup
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import com.benoitthore.words.data.WordsData
import com.benoitthore.words.features.utils.delegate
import com.benoitthore.words.utils.GenericAdapterViewModel
import com.benoitthore.words.utils.GenericViewModelAdapter
import splitties.views.backgroundColor
import splitties.views.dsl.core.button
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.view

class HomeView : EViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val addButton = context.button { text = "Add" }
    private val rvAdapter = GenericViewModelAdapter<WordViewModel>()
    private val rv: RecyclerView = context.view {
        backgroundColor = Color.RED
        adapter = rvAdapter
        layoutManager = LinearLayoutManager(context)
    }

    var words: List<WordsData> = emptyList()
        set(value) {
            field = value + WordsData("TEST_A", "TEST_B")
            rvAdapter.updateList(field.map { WordViewModel(it) })
        }

    init {
        val testTV = textView { backgroundColor = Color.BLUE; text = "working" }

        val layout: ELayout = listOf(addButton, rv, testTV)
                .laid()
                .stackedTopCenter()
                .arranged(center)


        transitionTo(layout, animate = false)


    }


    fun onAddClicked(block: () -> Unit) {
        addButton.setOnClickListener { block() }
    }

}


class WordItemView : EViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val wordATV = textView()
    private val wordBTV = textView()

    var wordA by wordATV.delegate()
    var wordB by wordBTV.delegate()

    init {
        val layout: ELayout =
                listOf(wordATV, wordBTV)
                        .laid()
                        .stackedRightCenter(16.dp)
                        .arranged(center)


        transitionTo(layout, animate = false)
    }
}


private class WordViewModel(val item: WordsData) : GenericAdapterViewModel {
    override val buildView: (parent: ViewGroup) -> View
        get() = { parent ->
            WordItemView(parent.context)
        }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder.itemView as? WordItemView)?.let {
            it.wordA = item.wordA
            it.wordB = item.wordB
        } ?: TODO("Whoops")
    }
}