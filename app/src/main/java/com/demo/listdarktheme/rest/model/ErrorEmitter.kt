package com.demo.listdarktheme.rest.model

import com.demo.listdarktheme.utils.ErrorType

interface ErrorEmitter {
    fun onError(message: String)
    fun onError(errorType: ErrorType)
}