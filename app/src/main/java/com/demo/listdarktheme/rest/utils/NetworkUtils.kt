package com.demo.listdarktheme.rest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.demo.listdarktheme.rest.model.ErrorResponse
import com.demo.listdarktheme.rest.model.ResultWrapper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NetworkUtils {
    companion object {
        suspend inline fun <T> makeApiCall(
            crossinline callFunction: suspend () -> T
        ): ResultWrapper<T>? {
            return try {
                val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }
                ResultWrapper.Success(myObject)
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    throwable.printStackTrace()
                    Log.e(
                        "NetworkUtils",
                        "Call error: ${throwable.localizedMessage}",
                        throwable.cause
                    )
                    when (throwable) {
                        is IOException -> ResultWrapper.NetworkError
                        is HttpException -> {
                            val code = throwable.code()
                            val errorResponse = convertErrorBody(throwable)
                            ResultWrapper.GenericError(code, errorResponse)
                        }
                        else -> {
                            ResultWrapper.GenericError(null, null)
                        }
                    }
                }
                null
            }
        }

        fun convertErrorBody(throwable: HttpException): ErrorResponse? {
            return try {
                throwable.response()?.errorBody()?.source()?.let {
                    val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                    moshiAdapter.fromJson(it)
                }
            } catch (exception: Exception) {
                null
            }
        }

        fun isInternetAvailable(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                isConnected = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    activeNetworkInfo?.run {
                        isConnected = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return isConnected
        }
    }
}