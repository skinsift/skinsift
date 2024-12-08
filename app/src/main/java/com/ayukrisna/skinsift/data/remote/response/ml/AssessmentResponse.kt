package com.ayukrisna.skinsift.data.remote.response.ml

import com.ayukrisna.skinsift.data.remote.response.product.ProductListItem
import com.google.gson.annotations.SerializedName

data class AssessmentResponse(
	@field:SerializedName("Productlist")
	val productList: List<ProductListItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

//data class ProductlistItem(
//
//	@field:SerializedName("merk")
//	val merk: String? = null,
//
//	@field:SerializedName("url_gambar")
//	val urlGambar: String? = null,
//
//	@field:SerializedName("Id_Products")
//	val idProducts: Int? = null,
//
//	@field:SerializedName("nama_product")
//	val namaProduct: String? = null,
//
//	@field:SerializedName("deskripsi")
//	val deskripsi: String? = null
//)
