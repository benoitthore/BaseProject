package com.benoitthore.github.model

import com.benoitthore.base.lib.data.Mapper
import com.benoitthore.github.model.repository.GithubRepositoryDTO
import com.benoitthore.github.model.repository.GithubRepositoryModel

object Mappers {
    object GithubRepositories_DTOtoModel : Mapper<GithubRepositoryDTO, GithubRepositoryModel> {
        override fun invoke(dto: GithubRepositoryDTO): GithubRepositoryModel = GithubRepositoryModel(
                name = dto.name,
                description = dto.description,
                numberOfForks = dto.forks_count,
                numberOfStars = dto.stargazers_count,
                language = dto.language
        )
    }
}