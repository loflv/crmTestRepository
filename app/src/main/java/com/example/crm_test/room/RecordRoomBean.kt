package com.example.crm_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordRoomBean(var name: String, var title: String, var content: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}