package com.ayukrisna.skinsift.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DetailProduct? = null,

)

data class DetailProduct(

	@field:SerializedName("merk")
	val brand: String? = null,

	@field:SerializedName("kegunaan")
	val benefit: String? = null,

	@field:SerializedName("url_gambar")
	val imageUrl: String? = null,

	@field:SerializedName("jenis_kulit")
	val skinType: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("Id_Products")
	val id: Int? = null,

	@field:SerializedName("nama_product")
	val productName: String? = null,

	@field:SerializedName("kategori")
	val category: String? = null,

	@field:SerializedName("jenis_product")
	val productType: String? = null,

	@field:SerializedName("deskripsi")
	val description: String? = null,

	@field:SerializedName("no_BPOM")
	val bpom: String? = null,

	@field:SerializedName("key_ingredients")
	val keyIngredients: String? = null
)
