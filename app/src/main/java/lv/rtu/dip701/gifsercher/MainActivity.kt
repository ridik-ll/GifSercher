package lv.rtu.dip701.gifsercher

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: GiphyViewModel
    private lateinit var searchEditText: EditText
    private lateinit var gifRecyclerView: RecyclerView
    private val gifAdapter = GifAdapter(emptyList())
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, GiphyViewModelFactory(GiphyRepository()))[GiphyViewModel::class.java]

        searchEditText = findViewById(R.id.searchEditText)
        gifRecyclerView = findViewById(R.id.gifRecyclerView)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        gifRecyclerView.layoutManager = LinearLayoutManager(this)
        gifRecyclerView.adapter = gifAdapter

        // Add scroll listener to the RecyclerView
        gifRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Load more GIFs when reaching the end of the list and not currently loading
                if (!isLoading && totalItemCount <= (lastVisibleItem + 5)) { // 5 is the visibleThreshold
                    isLoading = true
                    viewModel.loadMoreGifs()
                }
            }
        })
    }

    private fun setupSearch() {
        val handler = Handler(Looper.getMainLooper())
        var searchRunnable: Runnable? = null

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }

            override fun afterTextChanged(editable: Editable?) {
                searchRunnable?.let { handler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    editable?.toString()?.let { viewModel.searchGifs(it) }
                }

                handler.postDelayed(searchRunnable!!, 300) // 300 ms delay for debouncing
            }
        })
    }

    private fun observeViewModel() {
        viewModel.gifs.observe(this, { gifs ->
            gifAdapter.updateGifs(gifs)
            isLoading = false // Reset loading state when new data is received
        })
    }
}
