package com.cakabara.librarybooks_api

data class Book(
    val id: Int,
    val title: String,
    val genre: String,
    val author: String,
    val year_publish: Int,
    val description: String,
    val image_url: String
)