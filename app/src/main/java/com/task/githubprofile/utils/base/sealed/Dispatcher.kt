package com.task.githubprofile.utils.base.sealed

sealed class Dispatcher {
    object Main : Dispatcher()
    object Background : Dispatcher()
    object LongOperation : Dispatcher()
}