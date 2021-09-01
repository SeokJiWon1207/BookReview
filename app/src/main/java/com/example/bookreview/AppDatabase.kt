package com.example.bookreview

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookreview.dao.HistoryDao
import com.example.bookreview.dao.ReviewDao
import com.example.bookreview.model.History
import com.example.bookreview.model.Review

// 2-3) History Entity 모델을 베이스로 하고 History의 메소드를 가지고 있는 Database생성
@Database(entities = [History::class, Review::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}