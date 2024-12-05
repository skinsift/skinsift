package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class FilterProductResponse(

	@field:SerializedName("Productlist")
	val data: List<ProductFilter?>? = null,

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ProductFilter(

	@field:SerializedName("jenis_kulit")
	val skinType: List<String?>? = null,

	@field:SerializedName("kategori")
	val category: List<String?>? = null
)
