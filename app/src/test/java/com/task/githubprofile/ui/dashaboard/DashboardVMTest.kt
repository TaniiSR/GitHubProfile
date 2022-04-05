package com.task.githubprofile.ui.dashaboard

import com.task.githubprofile.base.BaseTestCase
import com.task.githubprofile.base.getOrAwaitValue
import com.task.githubprofile.data.dtos.responsedtos.GitUser
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.domain.DataRepository
import com.task.githubprofile.domain.IDataRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DashboardVMTest : BaseTestCase() {
    // Subject under test
    lateinit var viewModel: DashboardVM

    // Use a fake UseCase to be injected into the viewModel
    lateinit var dataRepo: IDataRepository

    @Before
    fun setUp() {
        dataRepo = mockk<DataRepository>()
    }

    @Test
    fun `get profile repo list success`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Success<GitUser>> {
                every { data } returns mockk {
                    every { repos } returns listOf(mockk(), mockk())
                }
            }
            coEvery {
                dataRepo.getGithubProfiles(
                    query,
                    true
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getTrendyGithubRepos(query, true)

            //3-verify
            Assert.assertEquals(false, viewModel.repoLists.getOrAwaitValue().isNullOrEmpty())
        }
    }

    @Test
    fun `get profile list with local data success`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Success<GitUser>> {
                every { data } returns mockk {
                    every { repos } returns listOf(mockk(), mockk())
                }
            }
            coEvery {
                dataRepo.getGithubProfiles(
                    query,
                    false
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getTrendyGithubRepos(query, false)

            //3-verify
            Assert.assertEquals(false, viewModel.repoLists.getOrAwaitValue().isNullOrEmpty())
        }
    }

    @Test
    fun `get profile list error`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getGithubProfiles(
                    query,
                    true
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getTrendyGithubRepos(query, true)

            //3-verify
            Assert.assertEquals(true, viewModel.repoLists.getOrAwaitValue().isNullOrEmpty())
        }
    }

    @Test
    fun `get profile list with local data error`() {
        //1- Mock calls
        val query = ""
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getGithubProfiles(
                    query,
                    false
                )
            } returns response
            //2-Call
            viewModel = DashboardVM(dataRepo)
            viewModel.getTrendyGithubRepos(query, false)

            //3-verify
            Assert.assertEquals(true, viewModel.repoLists.getOrAwaitValue().isNullOrEmpty())
        }
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}