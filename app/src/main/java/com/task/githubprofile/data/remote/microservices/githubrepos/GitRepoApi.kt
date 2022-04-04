package com.task.githubprofile.data.remote.microservices.githubrepos

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.remote.baseclient.ApiResponse


interface GitRepoApi {
    suspend fun getProfiles(query: String): ApiResponse<GitUser>
}