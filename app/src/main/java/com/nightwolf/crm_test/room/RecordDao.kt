package com.nightwolf.crm_test.room

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface RecordDao {

    @Insert
    fun insertRecord(vararg bean: RecordRoomBean)


    @Query("select * from RecordRoomBean where id = :selectId")
    suspend fun findRecordById(selectId: Long): RecordRoomBean

    @Update
    fun updateRecord(bean: RecordRoomBean)


    @Query("SELECT * FROM RecordRoomBean ORDER BY name COLLATE NOCASE ASC")
    fun findAllRecord(): PagingSource<Int, RecordRoomBean>


    @Delete
    suspend fun delete(bean: RecordRoomBean)
}