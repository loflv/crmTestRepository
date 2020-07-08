package com.example.crm_test.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crm_test.util.MyApplication

@Database(entities = [RecordRoomBean::class], version = 3)
abstract class RecordDatabase : RoomDatabase() {
    abstract val recordDao: RecordDao

    companion object {
        var recordDb: RecordDatabase? = null
            get() {
                if (field == null) {
                    field = Room.databaseBuilder(
                        MyApplication.mContext,
                        RecordDatabase::class.java, "android_room_record_content.db"
                    )
                        .build()
                }
                return field
            }
            private set
    }
}