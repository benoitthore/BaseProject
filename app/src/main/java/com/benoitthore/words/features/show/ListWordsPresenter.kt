package com.benoitthore.words.features.show

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.benoitthore.words.data.WordsData
import com.benoitthore.words.utils.CoroutineAware
import com.benoitthore.words.data.WordsRepository
import kotlinx.coroutines.*

class ListWordsPresenter(val wordsRepo: WordsRepository,
                         override val scope: CoroutineScope,
                         override val mainThreadDispatcher: CoroutineDispatcher
) : CoroutineAware {

    private lateinit var view: ListWordsView

    interface ListWordsView {
        var words: List<WordsData>
    }

    fun start(view: ListWordsView) {
        this.view = view
        mainThreadCoroutine {
            view.words = wordsRepo.get()
        }
    }

    fun onAddClicked() {
        Toast.makeText(
                (view as Fragment).context,
                "ADD",
                Toast.LENGTH_SHORT
        ).show()
    }

}

