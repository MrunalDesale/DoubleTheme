package com.demo.listdarktheme.rest.repository

import com.demo.listdarktheme.rest.model.ErrorEmitter
import com.demo.listdarktheme.utils.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

abstract class RemoteRepository {
    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    suspend inline fun <T> getApiCall(
        emitter: ErrorEmitter,
        crossinline callFun: suspend () -> T
    ): T? {
        return try {
            val myObj = withContext(Dispatchers.IO) { callFun.invoke() }
            myObj
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                when (e) {
                    is HttpException -> {
                        if (e.code() == 401) emitter.onError(ErrorType.SESSION_EXPIRED)
                        else {
                            val body = e.response()?.errorBody()
                            emitter.onError(getErrorMessage(body))
                        }
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}