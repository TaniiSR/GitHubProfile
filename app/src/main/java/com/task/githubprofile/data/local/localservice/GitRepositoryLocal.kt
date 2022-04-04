package com.task.githubprofile.data.local.localservice

import com.task.githubprofile.data.dtos.responsedtos.Profile
import javax.inject.Inject

class GitRepositoryLocal @Inject constructor(private val gitLocalDao: GitLocalDao) :
    GitRepoDbService {
    override suspend fun getProfiles() = gitLocalDao.getAllTProfiles()

    override suspend fun insertProfiles(profiles: List<Profile>) =
        gitLocalDao.insertAllProfiles(profiles = profiles)
}