package com.demo.listdarktheme.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demo.listdarktheme.rest.model.ErrorEmitter
import com.demo.listdarktheme.utils.ErrorType

open class BaseViewModel(application: Application) : AndroidViewModel(application),
    ErrorEmitter {
    var showLoader: MutableLiveData<Boolean> = MutableLiveData()
    var showMessage: MutableLiveData<String> = MutableLiveData()
    var showErrorType: MutableLiveData<ErrorType> = MutableLiveData()

    override fun onError(errorMessage: String) {
        showMessage.postValue(errorMessage)
    }

    override fun onError(errorType: ErrorType) {
        showErrorType.postValue(errorType)
    }
}