package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailIngredientsResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: Ingredient? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Ingredient(

	@field:SerializedName("Id_Ingredients")
	val idIngredients: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("kategoriidn")
	val kategoriidn: String? = null,

	@field:SerializedName("keyidn")
	val keyidn: String? = null,

	@field:SerializedName("benefitidn")
	val benefitidn: String? = null,

	@field:SerializedName("deskripsiidn")
	val deskripsiidn: String? = null
)
