package com.example.bookreview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreview.model.History

// Room 2-2) DB에 접근해 질의를 수행할 Dao 파일 생성.
// Query를 메소드로 작성해줘야한다.
@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword: String)
}