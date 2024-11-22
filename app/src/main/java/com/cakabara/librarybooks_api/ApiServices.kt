package com.cakabara.librarybooks_api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("/books")
    suspend fun getBooks(): List<Book>
    @GET("/books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Book
}


