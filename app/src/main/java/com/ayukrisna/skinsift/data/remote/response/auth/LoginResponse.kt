package com.ayukrisna.skinsift.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

    @field:SerializedName("status_code")
	val statusCode: Int? = null,

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("detail")
	val detail: String? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
