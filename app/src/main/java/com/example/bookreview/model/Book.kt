package com.example.bookreview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// Retrofit 1-1) REST API로 받아올 데이터를 받아올 모델 클래스 생성
// 서버에서 받는 데이터 모델
@Parcelize // 직렬화가능한 data class
data class Book(
    // json 데이터의 변수명과 다르게 변수를 짓는 경우
    // @SerializedName("속성명")으로 설정 가능
    @SerializedName("itemId") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("priceSales") val priceSales: String,
    @SerializedName("coverSmallUrl") val coverSmallUrl: String,
    @SerializedName("mobileLink") val mobileLink: String
): Parcelable
