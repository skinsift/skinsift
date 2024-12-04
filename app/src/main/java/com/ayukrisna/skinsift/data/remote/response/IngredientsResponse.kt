package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("Ingredientlist")
	val ingredientlist: List<IngredientListItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null
)

data class IngredientListItem(

	@field:SerializedName("Id_Ingredients")
	val idIngredients: Int,

	@field:SerializedName("nama")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("benefitidn")
	val category: String? = null
)
