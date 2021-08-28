package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bookreview.api.BookService
import com.example.bookreview.model.BestSellerDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainAcivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrofit Build
        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBook("36494DB4C76866B2F696E9672624BEBD4E7A0D7DEDF9B8D84F5674CCBC297131")
            .enqueue(object: Callback<BestSellerDTO> {
                override fun onResponse(call: Call<BestSellerDTO>, response: Response<BestSellerDTO>) {

                    if (response.isSuccessful.not()) {
                        Log.e(TAG,"not success")
                        return
                    }

                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    Log.d(TAG, t.toString())
                }

            })

    }


}