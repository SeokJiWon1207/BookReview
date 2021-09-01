package com.example.bookreview.model

import com.google.gson.annotations.SerializedName

// Retrofit 1-1) REST API로 받아올 데이터를 받아올 모델 클래스 생성
// 검색 기능에서 사용하는 SearchBook Data Model
data class SearchBookDTO(
    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<Book>
)
