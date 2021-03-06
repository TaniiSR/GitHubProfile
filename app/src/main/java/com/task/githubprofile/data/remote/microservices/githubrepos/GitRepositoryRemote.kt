package com.task.githubprofile.data.remote.microservices.githubrepos

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.data.remote.baseclient.BaseRepository
import javax.inject.Inject

class GitRepositoryRemote @Inject constructor(
    private val service: GitRepoRetroService
) : BaseRepository(), GitRepoApi {

    companion object {
        const val URL_GET_PROFILES = "search/users"
        const val URL_GET_USER = "users/{username}"
    }

    override suspend fun getProfiles(query: String): ApiResponse<GitUser> {
        return executeSafely(call = {
            service.getProfiles(query)
        })
    }

    override suspend fun getUser(userName: String): ApiResponse<User> {
        return executeSafely(call = {
            service.getUser(userName)
        })
    }
}