package com.ayukrisna.skinsift.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class DeleteUserResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: DeleteId? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DeleteId(

	@field:SerializedName("user_id")
	val userId: String? = null
)
