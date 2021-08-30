package com.example.bookreview.model

import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName

// 서버에서 받는 데이터 모델
@VersionedParcelize
data class Book(
    // json 데이터의 변수명과 다르게 변수를 짓는 경우
    // @SerializedName("속성명")으로 설정 가능
    @SerializedName("itemId") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("priceSales") val priceSales: String,
    @SerializedName("coverSmallUrl") val coverSmallUrl: String,
    @SerializedName("mobileLink") val mobileLink: String
)
