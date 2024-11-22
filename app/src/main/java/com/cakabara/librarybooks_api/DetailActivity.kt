package com.cakabara.librarybooks_api
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
            val repository = Repository(RetrofitInstance.apiService)
            detailViewModel = BooksViewModel(repository)

            observeViewModel()
            detailViewModel.fetchBookById(bookId) // Fetch the book details by ID
        }
    }

    private fun observeViewModel() {
        detailViewModel.bookLiveData.observe(this, Observer { book ->
            binding.tvDetailJudul.text = book.title
            binding.tvDetailGenre.text = "Genre: ${book.genre}"
            binding.tvDetailPengarang.text = "Pengarang: ${book.author}"
            binding.tvDetailTahunTerbit.text = "Tahun Terbit: ${book.year_publish}"
            binding.tvDetailDeskripsi.text = book.description

            Glide.with(binding.ivDetailCover.context)
                .load(book.image_url)
                .into(binding.ivDetailCover)
        })
    }
}
