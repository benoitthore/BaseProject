package com.benoitthore.github.model.repository

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.benoitthore.base.R

data class GithubRepositoryModel(
        val name: String? = null,
        val description: String? = null,
        val numberOfStars: Int? = null,
        val numberOfForks: Int? = null,
        val language: String? = null
)
