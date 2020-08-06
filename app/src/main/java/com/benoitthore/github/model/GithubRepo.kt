package com.benoitthore.github.model

import com.benoitthore.base.lib.data.ApiResponse
import com.benoitthore.base.lib.data.Mapper
import com.benoitthore.base.lib.data.invoke
import com.benoitthore.base.lib.data.toApiResponse
import com.benoitthore.github.model.repository.GithubOrganisation
import com.benoitthore.github.model.repository.GithubRepositoryDTO
import com.benoitthore.github.model.repository.GithubRepositoryModel
import kotlinx.coroutines.withTimeout

class GithubRepo(
        private val service: GithubService,
        private val dtoToModel: Mapper<GithubRepositoryDTO, GithubRepositoryModel>) {

    suspend fun getOrgData(organisation: GithubOrganisation): ApiResponse<List<GithubRepositoryModel>> {
        return kotlin.runCatching {
            withTimeout(5000L) {
                val retrofitResponse = service.getRepositoryList(organisation.id)
                val dtoList = retrofitResponse.toApiResponse { dtoToModel(it) }
                return@withTimeout dtoList
            }
        }.getOrNull() ?: ApiResponse.NetworkError()
    }
}


