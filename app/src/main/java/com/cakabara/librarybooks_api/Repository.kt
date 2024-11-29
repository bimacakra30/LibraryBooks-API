package com.cakabara.librarybooks_api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Repository(private val apiServices: ApiServices, private val context: Context, private val bookDao: BookDao) {

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    suspend fun getBooks(): List<Book> {
        return if (isNetworkAvailable()) {
            val books = apiServices.getBooks()
            bookDao.insertBooks(books)
            books
        } else {
            bookDao.getAllBooks()
        }
    }

    suspend fun getBookById(id: Int): Book {
        return if (isNetworkAvailable()) {
            val book = apiServices.getBookById(id)
            bookDao.insertBooks(listOf(book))
            book
        } else {
            bookDao.getBookById(id)
        }
    }
}
