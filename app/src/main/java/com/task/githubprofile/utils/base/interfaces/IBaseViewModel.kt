package com.task.githubprofile.utils.base.interfaces

import com.task.githubprofile.utils.base.SingleClickEvent

interface IBaseViewModel {
    val clickEvent: SingleClickEvent
    fun onClick(view: android.view.View)
}