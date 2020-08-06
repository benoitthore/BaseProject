package com.benoitthore.github.ui

import com.benoitthore.base.R

private val programmingLanguageColorMap = sequenceOf(
        "Kotlin" to R.color.language_kotlin,
        "Java" to R.color.language_java,
        "C" to R.color.language_c_sharp,
        "Ruby" to R.color.language_ruby,
        "Php" to R.color.language_php,
        "Objective-C" to R.color.language_objective_c,
        "Python" to R.color.language_python,
        "Swift" to R.color.language_swift,
        "JavaScript" to R.color.language_javascript,
        "Go" to R.color.language_go
).map { (s,c)->s.toLowerCase() to c }.toMap()

fun getProgrammingLanguageColor(language: String) = programmingLanguageColorMap[language.toLowerCase()]
        ?: R.color.language_other