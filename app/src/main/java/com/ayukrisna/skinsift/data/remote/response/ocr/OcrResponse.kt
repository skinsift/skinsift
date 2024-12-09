package com.ayukrisna.skinsift.data.remote.response.ocr

import com.google.gson.annotations.SerializedName

data class OcrResponse(

	@field:SerializedName("warnings")
	val warnings: List<WarningsItem?>? = null,

	@field:SerializedName("matched_ingredients")
	val matchedIngredients: List<MatchedIngredientsItem?>? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class WarningsItem(

	@field:SerializedName("details")
	val details: List<String?>? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class MatchedIngredientsItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("kategoriidn")
	val kategoriidn: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
