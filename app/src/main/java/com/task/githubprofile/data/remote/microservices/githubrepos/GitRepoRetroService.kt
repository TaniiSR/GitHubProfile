package com.task.githubprofile.data.remote.microservices.githubrepos

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRepoRetroService {
    //Get the github profiles
    @GET(GitRepositoryRemote.URL_GET_PROFILES)
    suspend fun getProfiles(
        @Query("q") query: String = ""
    ): Response<GitUser>
}