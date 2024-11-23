package com.ayukrisna.skinsift.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class StoryResponse(
	val listStory: List<ListStoryItem?>? = null,
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class ListStoryItem(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Double? = null,
	val id: String? = null,
	val lat: Double? = null
) : Parcelable
