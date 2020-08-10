package com.example.crm_test.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crm_test.MyApplication

@Database(entities = [RecordRoomBean::class], version = 4, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var recordDb: RecordDatabase? = null

        fun get(): RecordDatabase {
            return recordDb ?: let {
                Room.databaseBuilder(
                    MyApplication.mContext,
                    RecordDatabase::class.java, "android_room_record_content.db"
                ).build()
            }
        }
    }
}