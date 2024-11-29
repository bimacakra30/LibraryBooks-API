package com.cakabara.librarybooks_api

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BukuAdapter
    private lateinit var booksViewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = BukuAdapter { bookId -> onBookClick(bookId) }
        recyclerView.adapter = adapter

        val bookDao = AppDatabase.getDatabase(this).bookDao()
        val repository = Repository(RetrofitInstance.apiService, this, bookDao)
        booksViewModel = BooksViewModel(repository)

        errorMessage()
        observeViewModel()
        booksViewModel.fetchBooks()
    }

    private fun errorMessage() {
        booksViewModel.errorLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        booksViewModel.booksLiveData.observe(this) { books ->
            adapter.submitList(books)
        }
    }

    private fun onBookClick(bookId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("BOOK_ID", bookId)
        startActivity(intent)
    }
}