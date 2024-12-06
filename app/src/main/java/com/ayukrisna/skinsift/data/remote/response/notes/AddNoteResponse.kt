package com.ayukrisna.skinsift.data.remote.response.notes

import com.google.gson.annotations.SerializedName

data class AddNoteResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
