package com.example.crm_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordDao {

    @Insert
    fun insertRecord(vararg bean: RecordRoomBean)


    @Query("select * from RecordRoomBean where id = :selectId")
    fun findRecordById(selectId: Long): RecordRoomBean

    @Update
    fun updateRecord(bean: RecordRoomBean)


    @Query("select * from RecordRoomBean")
    fun findAllRecord(): List<RecordRoomBean>
}