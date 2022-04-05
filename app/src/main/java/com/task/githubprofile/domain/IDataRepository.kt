package com.task.githubprofile.domain

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.data.remote.baseclient.ApiResponse

interface IDataRepository {
    suspend fun getGithubProfiles(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GitUser>

    suspend fun getGithubUser(
        userName: String,
        isRefresh: Boolean
    ): ApiResponse<User>
}