package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.bookreview.adapter.BookAdapter
import com.example.bookreview.adapter.HistoryAdapter
import com.example.bookreview.api.BookService
import com.example.bookreview.databinding.ActivityMainBinding
import com.example.bookreview.model.BestSellerDTO
import com.example.bookreview.model.History
import com.example.bookreview.model.SearchBookDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainAcivity"
        private const val BASE_URL = "https://book.interpark.com/"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var historyadapter: HistoryAdapter
    private lateinit var bookService: BookService
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBookRecyclerView()
        initHistoryRecyclerView()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()

        // Retrofit Build
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // baseUrl은 꼭 '/' 로 끝나야 함, 아니면 예외 발생
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Retrofit instance로 interface 객체 구현
        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBook(getString(R.string.interpark_apikey))
            .enqueue(object :
                Callback<BestSellerDTO> { // 비동기 enqueue 작업으로 실행, 통신종료 후 이벤트 처리를 위해 Callback ㅡㅇ록
                // Success Case -> MainThread에서 처리
                override fun onResponse(
                    call: Call<BestSellerDTO>,
                    response: Response<BestSellerDTO>
                ) {

                    if (response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        // submitList(MutableList<T> list) : 리스트 데이터를 교체할 때 사용
                        adapter.submitList(it.books)
                    }

                }

                // Fail Case -> MainThread에서 처리
                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    t.message?.let { Log.d(TAG, it) }
                }

            })

    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun initHistoryRecyclerView() {
        historyadapter = HistoryAdapter(histroyDeleteClickedListener =  {
            deleteSearchKeyword(it)
        })

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyadapter

        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }

    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interpark_apikey), keyword)
            .enqueue(object :
                Callback<SearchBookDTO> { // 비동기 enqueue 작업으로 실행, 통신종료 후 이벤트 처리를 위해 Callback ㅡㅇ록
                // Success Case -> MainThread에서 처리
                override fun onResponse(
                    call: Call<SearchBookDTO>,
                    response: Response<SearchBookDTO>
                ) {
                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if (response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        adapter.submitList(it.books) // recyclerView 갱신
                    }
                }

                // Fail Case -> MainThread에서 처리
                override fun onFailure(call: Call<SearchBookDTO>, t: Throwable) {
                    t.message?.let { Log.d(TAG, it) }

                }
            })
    }

    private fun showHistoryView() {
        Thread {
            val keywords = db.historyDao().getAll().reversed() // 최신순으로 가져오기
            // UI 처리
            runOnUiThread {
                binding.historyRecyclerView.isVisible = true
                historyadapter.submitList(keywords.orEmpty())
            }
        }.start()

        binding.historyRecyclerView.isVisible = true
    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }

}