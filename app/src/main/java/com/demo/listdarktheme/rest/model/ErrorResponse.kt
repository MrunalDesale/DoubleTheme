package com.demo.listdarktheme.rest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(var message: String? = "")