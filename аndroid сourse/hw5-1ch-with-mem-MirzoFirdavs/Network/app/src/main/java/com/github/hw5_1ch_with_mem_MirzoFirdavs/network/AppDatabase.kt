package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao
}