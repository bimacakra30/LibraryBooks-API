package com.cakabara.librarybooks_api

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

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

        val repository = Repository(RetrofitInstance.apiService)
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
            sendToFirebase(books)
        }
    }
    private fun onBookClick(bookId: Int) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("BOOK_ID",bookId)
        startActivity(intent)
    }
    private fun sendToFirebase(books: List<Book>) {
        val myRef = FirebaseDatabase.getInstance().getReference("books")
        books.forEach { book ->
            myRef.child(book.id.toString()).setValue(book)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Data buku berhasil dikirim kedalam Firebase", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Gagal mengirim data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}