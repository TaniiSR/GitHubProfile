package com.task.githubprofile.ui.detail

import com.task.githubprofile.base.BaseTestCase
import com.task.githubprofile.base.getOrAwaitValue
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.domain.DataRepository
import com.task.githubprofile.domain.IDataRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailVMTest : BaseTestCase() {
    // Subject under test
    lateinit var viewModel: DetailVM

    // Use a fake UseCase to be injected into the viewModel
    lateinit var dataRepo: IDataRepository

    @Before
    fun setUp() {
        dataRepo = mockk<DataRepository>()
    }

    @Test
    fun `get user success`() {
        //1- Mock calls
        val user = ""
        runTest {
            val response = mockk<ApiResponse.Success<User>> {
                every { data } returns mockk(relaxed = true)
            }
            coEvery {
                dataRepo.getGithubUser(
                    user,
                    true
                )
            } returns response
            //2-Call
            viewModel = DetailVM(dataRepo)
            viewModel.getGithubUser(user, true)

            //3-verify
            Assert.assertEquals(true, viewModel.user.getOrAwaitValue() != null)
        }
    }

    @Test
    fun `get user local success`() {
        //1- Mock calls
        val user = ""
        runTest {
            val response = mockk<ApiResponse.Success<User>> {
                every { data } returns mockk(relaxed = true)
            }
            coEvery {
                dataRepo.getGithubUser(
                    user,
                    false
                )
            } returns response
            //2-Call
            viewModel = DetailVM(dataRepo)
            viewModel.getGithubUser(user, false)

            //3-verify
            Assert.assertEquals(true, viewModel.user.getOrAwaitValue() != null)
        }
    }

    @Test
    fun `get user error`() {
        //1- Mock calls
        val user = ""
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getGithubUser(
                    user,
                    true
                )
            } returns response
            //2-Call
            viewModel = DetailVM(dataRepo)
            viewModel.getGithubUser(user, true)

            //3-verify
            Assert.assertEquals(true, viewModel.user.getOrAwaitValue() == null)
        }
    }

    @Test
    fun `get user local error`() {
        //1- Mock calls
        val user = ""
        runTest {
            val response = mockk<ApiResponse.Error>()
            coEvery {
                dataRepo.getGithubUser(
                    user,
                    false
                )
            } returns response
            //2-Call
            viewModel = DetailVM(dataRepo)
            viewModel.getGithubUser(user, false)

            //3-verify
            Assert.assertEquals(true, viewModel.user.getOrAwaitValue() == null)
        }
    }
}