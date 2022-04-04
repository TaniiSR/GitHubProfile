package com.task.githubprofile.ui.dashaboard

import android.text.TextWatcher
import androidx.lifecycle.LiveData
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.ui.dashaboard.adapter.RepoListAdapter
import com.task.githubprofile.utils.base.interfaces.IBaseViewModel
import com.task.githubprofile.utils.base.sealed.UIEvent

interface IDashboardVM : IBaseViewModel {
    val uiState: LiveData<UIEvent>
    val repoLists: LiveData<List<Profile>>
    val query: String
    val adaptor: RepoListAdapter
    val watcher: TextWatcher
    fun fetchFreshData()
    fun getTrendyGithubRepos(query: String, isRefresh: Boolean)
}