package com.example.bookreview.api

import com.example.bookreview.model.SearchBookDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.bookreview.model.BestSellerDTO

interface BookService {

    // output 형식은 json으로 고정
    // @GET - Read, 정보 조회용도, URL에 모두 표현
    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyWord: String
    ): Call<SearchBookDTO>
    // Call은 응답이 왔을때 Callback으로 불려질 타입


    // 베스트셀러 api 검색 카테코리는 100(국내도서)으로 고정
    @GET("/api/bestSeller.api?categoryId=100&output=json")
    fun getBestSellerBook(
        @Query("key") apiKey: String
    ): Call<BestSellerDTO>

}