package com.task.githubprofile.data.local.localservice

import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.data.dtos.responsedtos.User

interface GitRepoDbService {
    suspend fun getProfiles(): List<Profile>
    suspend fun insertProfiles(profiles: List<Profile>)
    suspend fun insertUser(user: User)
    suspend fun getUser(userName: String): User?
}