package com.benoitthore.github

import com.benoitthore.base.R
import com.benoitthore.base.lib.MyDispatchers
import com.benoitthore.base.lib.data.createService
import com.benoitthore.github.model.GithubRepo
import com.benoitthore.github.model.GithubService
import com.benoitthore.github.model.Mappers
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val appModule = module {

    val githubRepositoryDTOMapperQualifier = StringQualifier("github repo dto mapper")
    single(githubRepositoryDTOMapperQualifier) { Mappers.GithubRepositories_DTOtoModel }

    single { createService<GithubService>(androidContext().getString(R.string.api_base_url)) }
    single { GithubRepo(get(), get(githubRepositoryDTOMapperQualifier)) }
    single { MyDispatchers() }
    viewModel { GithubRepositoryViewModel(get(), get()) }
}