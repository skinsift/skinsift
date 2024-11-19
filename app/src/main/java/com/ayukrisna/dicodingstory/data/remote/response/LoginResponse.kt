package com.ayukrisna.dicodingstory.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginResponse(
	val loginResult: LoginResult? = null,
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class LoginResult(
	val name: String? = null,
	val userId: String? = null,
	val token: String? = null
) : Parcelable
