package com.nightwolf.crm_test.util

import android.content.Context
import android.content.SharedPreferences
import com.nightwolf.crm_test.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SharedPreferencesRepository {

    suspend fun putContent(block: suspend SharedPreferences.Editor.() -> Unit) {
        withContext(Dispatchers.IO) {
            block(
                MyApplication.mContext.getSharedPreferences(
                    "login",
                    Context.MODE_PRIVATE
                ).edit()
            )
        }
    }

}