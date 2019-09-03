package com.benoitthore.words

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import org.koin.dsl.module.module

class WordsApp : Application() {

    private val appModule = module {
        single { WordsRepository() }
        factory { (scope: CoroutineScope) ->
            AddWordPresenter(get(),
                    scope = scope,
                    mainThreadDispatcher = Dispatchers.Main)
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}


class WordsRepository {
    private val words = mutableListOf(
            "1" to "A",
            "2" to "B",
            "3" to "C"
    )
            .map { (a, b) -> WordsData(a, b) }

    suspend fun get(): List<WordsData> {
        return words
    }
}

data class WordsData(val wordA: String, val wordB: String)


class AddWordPresenter(val wordsRepo: WordsRepository,
                       override val scope: CoroutineScope,
                       override val mainThreadDispatcher: CoroutineDispatcher
) : CoroutineAware {

    interface AddWordView {
        fun updateWords(words: List<WordsData>)
    }

    fun start(view: AddWordView) {
        mainThreadCoroutine { }
    }
}

interface CoroutineAware {
    val scope: CoroutineScope
    val mainThreadDispatcher: CoroutineDispatcher

    fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) {
        scope.launch(mainThreadDispatcher, block = block)
    }
}

