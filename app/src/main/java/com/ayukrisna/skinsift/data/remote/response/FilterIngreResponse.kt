package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class FilterIngreResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: Filter? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Filter(

	@field:SerializedName("rating")
	val rating: List<String>? = null,

	@field:SerializedName("benefitidn")
	val benefitidn: List<String>? = null
)
