package com.demo.listdarktheme.rest.repository

import android.util.Log
import com.demo.listdarktheme.rest.api.RestApi
import com.demo.listdarktheme.rest.api.RetrofitBuilder
import com.demo.listdarktheme.rest.mapper.DataMapper
import com.demo.listdarktheme.rest.model.ErrorEmitter
import com.demo.listdarktheme.rest.model.Recipe
import com.demo.listdarktheme.ui.model.RecipeModel
import com.demo.listdarktheme.utils.ErrorType
import com.demo.listdarktheme.utils.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {
    private val restApi: RestApi = RetrofitBuilder.apiInterface
    private val mapper = DataMapper()

    suspend fun getResponse(pageNo: Int, size: Int): List<RecipeModel> {
        restApi.getApiResult(pageNo, size).let {
            return mapper.mapRecipe(it)
        }
    }

    suspend inline fun <T> makeApiCall(
        emitter: ErrorEmitter,
        crossinline callFunction: suspend () -> T
    ): T? {
        return try {
            val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }
            myObject
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                Log.e("BaseRemoteRepo", "Call error: ${e.localizedMessage}", e.cause)
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 401 -> emitter.onError(ErrorType.SESSION_EXPIRED)
                            e.code() == 404 -> {
                                emitter.onError(ErrorType.NOT_FOUND)
                                val body = e.response()?.errorBody()
                                emitter.onError(ErrorUtils.getErrorMessage(body))
                            }
                            else -> {
                                val body = e.response()?.errorBody()
                                emitter.onError(ErrorUtils.getErrorMessage(body))
                            }
                        }
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is UnknownHostException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    is NullPointerException -> emitter.onError(ErrorType.UNKNOWN)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }
}