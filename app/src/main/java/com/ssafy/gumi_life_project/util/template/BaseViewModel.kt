package com.ssafy.gumi_life_project.util.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun showProgress() {
        _isLoading.value = true
    }

    protected fun hideProgress() {
        _isLoading.value = false
    }
}