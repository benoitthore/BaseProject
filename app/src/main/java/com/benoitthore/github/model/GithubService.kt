package com.benoitthore.github.model

import com.benoitthore.github.model.repository.GithubRepositoryDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/orgs/{orgName}/repos")
    suspend fun getRepositoryList(@Path("orgName") orgName: String) : Response<List<GithubRepositoryDTO>>

}
