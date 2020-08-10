package com.example.crm_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordRoomBean(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var title: String = "",
    var content: String = "",
    var workContent: String = "",
    var readAble: Boolean = false  //是否已读
)