package com.task.githubprofile.domain

import com.task.githubprofile.base.BaseTestCase
import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.local.localservice.GitRepoDbService
import com.task.githubprofile.data.local.localservice.GitRepositoryLocal
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.data.remote.microservices.githubrepos.GitRepoApi
import com.task.githubprofile.data.remote.microservices.githubrepos.GitRepositoryRemote
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataRepositoryTest : BaseTestCase() {
    // Subject under test
    lateinit var dataRepository: DataRepository

    // Use a fake UseCase to be injected into the DataRepository
    lateinit var remoteData: GitRepoApi
    lateinit var localData: GitRepoDbService

    @Before
    fun setUp() {
        remoteData = mockk<GitRepositoryRemote>()
        localData = mockk<GitRepositoryLocal>()
    }

    @Test
    fun `get trendy repo list success`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Success<GitUser>> {
                every { data } returns mockk {
                    every { repos } returns listOf(mockk(), mockk())
                }
            }
            val profiles = response.data.repos ?: listOf()

            coEvery {
                localData.insertProfiles(profiles)
            } returns Unit

            coEvery {
                localData.getProfiles()
            } returns profiles

            coEvery {
                remoteData.getProfiles(
                    query
                )
            } returns response
            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            dataRepository.getGithubProfiles(query, true)

            //3-verify
            coVerify { dataRepository.getGithubProfiles(query, true) }

        }
    }

    @Test
    fun `get trendy repo list with local success`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Success<GitUser>> {
                every { data } returns mockk {
                    every { repos } returns listOf(mockk(), mockk())
                }
            }
            val profiles = response.data.repos ?: listOf()

            coEvery {
                localData.getProfiles()
            } returns profiles

            //2-Call
            dataRepository = DataRepository(remoteData, localData)
            val mockedCallResponse = dataRepository.getGithubProfiles(query, false)

            //3-verify
            when (mockedCallResponse) {
                is ApiResponse.Success -> {
                    Assert.assertEquals(profiles, mockedCallResponse.data.repos)
                }
            }
        }
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}