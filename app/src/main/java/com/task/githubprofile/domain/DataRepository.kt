package com.task.githubprofile.domain

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.dtos.responsedtos.User
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

    override suspend fun getGithubUser(
        userName: String,
        isRefresh: Boolean
    ): ApiResponse<User> {
        val user = localRepository.getUser(userName)
        return when {
            !isRefresh && user != null -> {
                ApiResponse.Success(
                    200,
                    user
                )
            }
            else -> {
                val response = remoteRepository.getUser(userName)
                if (response is ApiResponse.Success) {
                    localRepository.insertUser(response.data)
                }
                response
            }
        }
    }
}