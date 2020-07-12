package com.example.crm_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordDao {

    @Insert
    suspend fun insertRecord(vararg bean: RecordRoomBean)


    @Query("select * from RecordRoomBean where id = :selectId")
    suspend fun findRecordById(selectId: Long): RecordRoomBean

    @Update
    suspend fun updateRecord(bean: RecordRoomBean)


    @Query("select * from RecordRoomBean")
    suspend fun findAllRecord(): List<RecordRoomBean>
}