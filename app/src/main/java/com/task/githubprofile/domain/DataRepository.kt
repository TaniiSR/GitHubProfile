package com.task.githubprofile.domain

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.local.localservice.GitRepoDbService
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.data.remote.microservices.githubrepos.GitRepoApi
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteRepository: GitRepoApi,
    private val localRepository: GitRepoDbService
) : IDataRepository {
    override suspend fun getGithubProfiles(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GitUser> {
        val repos = localRepository.getProfiles()
        return when {
            !isRefresh && repos.isNotEmpty() -> {
                ApiResponse.Success(
                    200,
                    GitUser(repos = repos)
                )
            }
            else -> {
                val response = remoteRepository.getProfiles(query)
                if (response is ApiResponse.Success) {
                    response.data.repos?.let { localRepository.insertProfiles(it) }
                }
                response
            }
        }
    }
}