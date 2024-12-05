package com.ayukrisna.skinsift.data.remote.response.notes

import com.google.gson.annotations.SerializedName

data class NotesResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("list")
	val list: List<Note?>? = null
)

data class Note(

	@field:SerializedName("preference")
	val preference: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null
)
