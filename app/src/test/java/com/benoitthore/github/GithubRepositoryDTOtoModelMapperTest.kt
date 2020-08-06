package com.benoitthore.github

import com.benoitthore.github.model.Mappers
import com.benoitthore.github.model.repository.GithubRepositoryDTO
import com.benoitthore.github.model.repository.GithubRepositoryModel
import org.junit.Test

import org.junit.Assert.*

class GithubRepositoryDTOtoModelMapperTest {

    @Test
    fun invoke() {
        val mapper = Mappers.GithubRepositories_DTOtoModel
        val dto = GithubRepositoryDTO(
                name = "name",
                description = "description",
                forks_count = 42,
                stargazers_count = 5
        )
        val model = GithubRepositoryModel(
                name = "name",
                description = "description",
                numberOfForks = 42,
                numberOfStars = 5
        )

        val result = mapper.invoke(dto)
        assertEquals(model, result)
    }

    @Test
    fun `language mapping`() {
        val mapper = Mappers.GithubRepositories_DTOtoModel
        val dto = GithubRepositoryDTO(
                language = "Kotlin"
        )
        val model = GithubRepositoryModel(
                language = "Kotlin"
        )

        val result = mapper.invoke(dto)
        assertEquals(model, result)
    }
}