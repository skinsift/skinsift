package com.ayukrisna.skinsift.domain.model

data class ProductModel (
    val id: Int,
    val name: String,
    val brand: String,
    val description: String,
    val imageUrl: String
)

data class ProductDetailModel (
    val id: Int,
    val name: String,
    val type: String,
    val category: String,
    val skinType : String,
    val brand: String,
    val ingredients: String,
    val keyIngredients: String,
    val description: String,
    val benefit: String,
    val bpom: String,
    val imageUrl: String
)