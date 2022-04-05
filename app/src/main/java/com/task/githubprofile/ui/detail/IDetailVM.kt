package com.task.githubprofile.ui.detail

import androidx.lifecycle.LiveData
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.utils.base.interfaces.IBaseViewModel
import com.task.githubprofile.utils.base.sealed.UIEvent

interface IDetailVM : IBaseViewModel {
    val uiState: LiveData<UIEvent>
    val user: LiveData<User?>
    var userName: String
    fun fetchFreshData()
    fun getGithubUser(userName: String, refresh: Boolean)
}