package com.benoitthore.github

import com.benoitthore.base.lib.data.ApiResponse
import com.benoitthore.base.lib.data.Mapper
import com.benoitthore.github.model.GithubRepo
import com.benoitthore.github.model.GithubService
import com.benoitthore.github.model.repository.GithubOrganisation
import com.benoitthore.github.model.repository.GithubRepositoryDTO
import com.benoitthore.github.model.repository.GithubRepositoryModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response

class GithubRepositoryTest {

    @Test
    fun `repository calls mapper on service result`() {
        runBlocking {

            val service = mock<GithubService>()
            val dtoToModel = mock<Mapper<GithubRepositoryDTO, GithubRepositoryModel>>()
            val repository = GithubRepo(service, dtoToModel)

            val list = listOf(GithubRepositoryDTO())
            val model = GithubRepositoryModel("some string")

            whenever(service.getRepositoryList(anyString())).thenReturn(Response.success(list))
            whenever(dtoToModel.invoke(list.first())).thenReturn(model)

            val returnedData = (repository.getOrgData(GithubOrganisation.Square) as ApiResponse.Success<List<GithubRepositoryModel>>).value
            assertEquals(listOf(model), returnedData)
        }
    }

}