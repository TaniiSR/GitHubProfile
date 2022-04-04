package com.task.githubprofile.utils.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githubprofile.utils.base.interfaces.IBaseViewModel
import com.task.githubprofile.utils.base.sealed.Dispatcher
import kotlinx.coroutines.*


abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    fun launch(dispatcher: Dispatcher = Dispatcher.Background, block: suspend () -> Unit): Job {
        return viewModelScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }

    fun <T> launchAsync(block: suspend () -> T): Deferred<T> =
        viewModelScope.async(Dispatchers.IO) {
            block()
        }

    override fun onClick(view: View) {
        clickEvent.setValue(view.id)
    }

}