package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookreview.adapter.BookAdapter
import com.example.bookreview.api.BookService
import com.example.bookreview.databinding.ActivityMainBinding
import com.example.bookreview.model.BestSellerDTO
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
    private var adapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit Build
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // baseUrl은 꼭 '/' 로 끝나야 함, 아니면 예외 발생
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Retrofit instance로 interface 객체 구현
        val bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBook(R.string.interpark_apikey.toString())
            .enqueue(object: Callback<BestSellerDTO> { // 비동기 enqueue 작업으로 실행, 통신종료 후 이벤트 처리를 위해 Callback ㅡㅇ록
                // Success Case -> MainThread에서 처리
                override fun onResponse(call: Call<BestSellerDTO>, response: Response<BestSellerDTO>) {

                    if (response.isSuccessful) {
                        Log.e(TAG,"onResponse, 성공")
                        return
                    } else {
                        Log.d(TAG,"onResponse, 실패")
                    }

                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }

                        adapter.submitList(it.books)
                    }

                }
                // Fail Case -> MainThread에서 처리
                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    t.message?.let { Log.d(TAG, it) }
                }

            })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

}