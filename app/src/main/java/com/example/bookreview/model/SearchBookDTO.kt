package com.example.bookreview.model

import com.google.gson.annotations.SerializedName

// 검색 기능에서 사용하는 SearchBook Data Model
data class SearchBookDTO(
    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<Book>
)
