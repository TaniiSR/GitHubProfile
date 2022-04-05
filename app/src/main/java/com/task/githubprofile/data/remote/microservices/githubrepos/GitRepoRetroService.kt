package com.task.githubprofile.data.remote.microservices.githubrepos

import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.dtos.responsedtos.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitRepoRetroService {
    //Get the github profiles
    @GET(GitRepositoryRemote.URL_GET_PROFILES)
    suspend fun getProfiles(
        @Query("q") query: String = ""
    ): Response<GitUser>

    @GET(GitRepositoryRemote.URL_GET_USER)
    suspend fun getUser(
        @Path("username") username: String = ""
    ): Response<User>
}