package com.example.bookreview.api

import com.example.bookreview.model.SearchBookDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.bookreview.model.BestSellerDTO

interface BookService {

    //output 형식은 json으로 고정
    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyWord: String
    ): Call<SearchBookDTO>

    // 베스트셀러 api 검색 카테코리는 100으로 고정 -> "국내도서 베스트셀러"
    @GET("/api/bestSeller.api?categoryId=100&output=json")
    fun getBestSellerBook(
        @Query("key") apiKey: String
    ): Call<BestSellerDTO>

}