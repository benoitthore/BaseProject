package com.benoitthore.words

import android.app.Application
import com.benoitthore.words.features.show.ListWordsPresenter
import com.benoitthore.words.data.WordsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module


class WordsApp : Application() {

    private val appModule = module {
        single { WordsRepository() }
        factory { (scope: CoroutineScope) ->
            ListWordsPresenter(get(),
                    scope = scope,
                    mainThreadDispatcher = Dispatchers.Main)
        }
    }


    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}


