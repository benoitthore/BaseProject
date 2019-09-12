package com.benoitthore.words.features.add

import com.benoitthore.words.data.WordsData
import com.benoitthore.words.utils.CoroutineAware
import com.benoitthore.words.data.WordsRepository
import kotlinx.coroutines.*

class AddWordPresenter(val wordsRepo: WordsRepository,
                       override val scope: CoroutineScope,
                       override val mainThreadDispatcher: CoroutineDispatcher
) : CoroutineAware {

    private lateinit var view: AddWordView

    interface AddWordView {
        var wordA: String
        var wordB: String

        fun close()
    }

    fun start(view: AddWordView) {
        this.view = view
        view.wordA = "Word AAA"
        view.wordB = "Word BBB"
    }

    fun onOkClicked() {
        mainThreadCoroutine {
            listOf(
                    async {
                        wordsRepo.put(
                                WordsData(
                                        wordA = view.wordA,
                                        wordB = view.wordB
                                )
                        )
                    },
                    async { delay(1000L) }
            ).awaitAll()
            view.close()
        }
    }

}

