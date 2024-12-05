package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("Productlist")
	val productList: List<ProductListItem?>? = null,

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ProductListItem(

	@field:SerializedName("merk")
	val brand: String? = null,

	@field:SerializedName("url_gambar")
	val imageUrl: String? = null,

	@field:SerializedName("Id_Products")
	val id: Int? = null,

	@field:SerializedName("nama_product")
	val productName: String? = null,

	@field:SerializedName("deskripsi")
	val description: String? = null
)
