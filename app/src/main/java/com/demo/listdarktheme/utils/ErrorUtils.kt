package com.demo.listdarktheme.utils

import okhttp3.ResponseBody
import org.json.JSONObject

class ErrorUtils {
    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
        fun getErrorMessage(responseBody: ResponseBody?): String {
            return try {
                val jsonObject = JSONObject(responseBody!!.string())
                when {
                    jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(
                        MESSAGE_KEY
                    )
                    jsonObject.has(ERROR_KEY) -> jsonObject.getString(
                        ERROR_KEY
                    )
                    else -> "Something went wrong"
                }
            } catch (e: Exception) {
                "Something went wrong"
            }
        }
    }
}