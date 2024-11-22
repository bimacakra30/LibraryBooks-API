package com.cakabara.librarybooks_api

class Repository(private val apiServices: ApiServices) {

    suspend fun getBooks(): List<Book> {
        return apiServices.getBooks()
    }
    suspend fun getBookById(id: Int): Book {
        return apiServices.getBookById(id)
    }
}