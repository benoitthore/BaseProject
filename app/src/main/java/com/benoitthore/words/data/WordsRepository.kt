package com.benoitthore.words.data

import com.benoitthore.enamel.core.findIndex

class WordsRepository {
    private val wordList: MutableList<WordsData> = mutableListOf()

    suspend fun get(): List<WordsData> {
        return wordList
    }

    suspend fun put(words: WordsData) {
        wordList.findIndex { words.wordA == it.wordA }?.let { index ->
            wordList[index] = words
        } ?: kotlin.run {
            wordList += words
        }
    }
}

data class WordsData(val wordA: String, val wordB: String)
