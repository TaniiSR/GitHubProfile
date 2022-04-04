package com.task.githubprofile.ui.dashaboard

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.data.remote.baseclient.ApiResponse
import com.task.githubprofile.domain.IDataRepository
import com.task.githubprofile.ui.dashaboard.adapter.RepoListAdapter
import com.task.githubprofile.utils.base.BaseViewModel
import com.task.githubprofile.utils.base.sealed.Dispatcher
import com.task.githubprofile.utils.base.sealed.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val dataRepository: IDataRepository
) : BaseViewModel(), IDashboardVM {
    private var _uiState: MutableLiveData<UIEvent> = MutableLiveData()
    override var uiState: LiveData<UIEvent> = _uiState

    private val _repoLists: MutableLiveData<List<Profile>> = MutableLiveData()
    override var repoLists: LiveData<List<Profile>> = _repoLists

    override var query: String = "q"
    override val adaptor: RepoListAdapter = RepoListAdapter(mutableListOf())
    private var job: Job? = null
    override fun fetchFreshData() {
        getTrendyGithubRepos(query = query, true)
    }

    override fun getTrendyGithubRepos(query: String, isRefresh: Boolean) {
        job = launch {
            _uiState.postValue(UIEvent.Loading)
            val response = dataRepository.getGithubProfiles(query, isRefresh)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _repoLists.value = response.data.repos ?: listOf()
                        _uiState.value = UIEvent.Success
                    }
                    is ApiResponse.Error -> {
                        _repoLists.value = listOf()
                        _uiState.value = UIEvent.Error(response.error.message)
                    }
                }
            }
        }
    }

    override val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            if (!s.isNullOrEmpty())
                launch(Dispatcher.Main) {
                    job?.cancel()
                    delay(1000)
                    getTrendyGithubRepos(s.toString(), true)
                }
        }
    }
}
