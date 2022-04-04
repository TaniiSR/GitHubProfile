package com.task.githubprofile.data.local.localservice

import com.task.githubprofile.data.dtos.responsedtos.Profile

interface GitRepoDbService {
    suspend fun getProfiles(): List<Profile>
    suspend fun insertProfiles(profiles: List<Profile>)
}