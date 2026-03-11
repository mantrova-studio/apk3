package com.polihov.restaurantshell

data class Restaurant(
    val id: Int,
    val name: String,
    val url: String,
    val logoResId: Int,
    val placeholderNote: String = ""
)
