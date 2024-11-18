package com.ayukrisna.dicodingstory.util

fun isNumber(toCheck: String): Boolean {
    return toCheck.toDoubleOrNull() != null
}