package com.task.githubprofile.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.domain.IDataRepository
import com.task.githubprofile.utils.base.BaseViewModel
import com.task.githubprofile.utils.base.sealed.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val dataRepository: IDataRepository
) : BaseViewModel(), IDetailVM {

    private val _uiState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiState: LiveData<UIEvent> = _uiState

    private val _user: MutableLiveData<User?> = MutableLiveData()
    override val user: LiveData<User?> = _user

    override var userName: String = ""

    override fun fetchFreshData() {
        getGithubUser(userName = userName, true)
    }

    override fun getGithubUser(userName: String, refresh: Boolean) {
        launch {
            _uiState.postValue(UIEvent.Loading)
            val response = dataRepository.getGithubUser(userName, refresh)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _user.value = response.data
                        _uiState.value = UIEvent.Success
                    }
                    is ApiResponse.Error -> {
                        _user.value = null
                        _uiState.value = UIEvent.Error(response.error.message)
                    }
                }
            }
        }
    }


}