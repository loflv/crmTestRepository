/*
 * Copyright (C),2007-2020, LonBon Technologies Co. Ltd. All Rights Reserved.
 */

package com.nightwolf.crm_test.ext

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nightwolf.crm_test.R

/**
 *@author liwu
 *@date 20-12-18
 *@describe  设置数字角标
 */
fun BottomNavigationView.setBadge(indexView: Int, number: Int) {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    val itemView = menuView.getChildAt(indexView) as BottomNavigationItemView
    val view = itemView.tag as TextView?
    if (view != null) {
        if (number >= 1) {
            view.visibility = View.VISIBLE
            view.text = number.toString()
        } else {
            view.visibility = View.GONE
        }
    } else {
        val badgeView =
            LayoutInflater.from(context).inflate(R.layout.app_layout_badge_view, menuView, false)
        itemView.addView(badgeView)
        val count = badgeView.findViewById<TextView>(R.id.tv_badge)
        itemView.tag = count
        if (number >= 1) {
            count.text = number.toString()
        } else {
            count.visibility = View.GONE
        }
    }

}
