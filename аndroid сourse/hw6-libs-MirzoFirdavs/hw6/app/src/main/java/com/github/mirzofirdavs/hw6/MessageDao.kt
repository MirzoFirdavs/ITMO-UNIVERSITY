package com.github.mirzofirdavs.hw6

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(image: List<Message>)

    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE id IN (:MessageIds)")
    fun findByIds(MessageIds: List<Int>): List<Message>
}