package com.ayukrisna.skinsift.util

fun isNumber(toCheck: String): Boolean {
    return toCheck.toDoubleOrNull() != null
}