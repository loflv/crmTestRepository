package com.nightwolf.crm_test.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView


fun TextView.atColor() {
    val style = SpannableStringBuilder()
    val origion: String = this.text.toString()
    //设置文字
    style.append(origion)

    if (origion.contains("@")) {
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#03a0ea"))

        val start = origion.indexOf('@')
        var end = origion.indexOf(' ', start + 1)

        if (end == -1) {
            end = origion.length
        }

        style.setSpan(
            foregroundColorSpan,
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = style
    }

}