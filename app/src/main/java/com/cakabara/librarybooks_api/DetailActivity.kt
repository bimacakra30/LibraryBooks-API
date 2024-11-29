package com.cakabara.librarybooks_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cakabara.librarybooks_api.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: BooksViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookId = intent.getIntExtra("BOOK_ID", -1)
        if (bookId != -1) {
            val bookDao = AppDatabase.getDatabase(this).bookDao()
            val repository = Repository(RetrofitInstance.apiService, this, bookDao)
            detailViewModel = BooksViewModel(repository)

            observeViewModel()
            detailViewModel.fetchBookById(bookId)
        }
    }

    private fun observeViewModel() {
        detailViewModel.bookLiveData.observe(this){ book ->
            binding.tvDetailJudul.text = book.title
            binding.tvDetailGenre.text = "Genre: ${book.genre}"
            binding.tvDetailPengarang.text = "Pengarang: ${book.author}"
            binding.tvDetailTahunTerbit.text = "Tahun Terbit: ${book.year_publish}"
            binding.tvDetailDeskripsi.text = book.description

            Glide.with(binding.ivDetailCover.context)
                .load(book.image_url)
                .into(binding.ivDetailCover)
        }

        detailViewModel.errorLiveData.observe(this) { errorMessage ->
            binding.tvDetailDeskripsi.text = errorMessage
        }
    }
}