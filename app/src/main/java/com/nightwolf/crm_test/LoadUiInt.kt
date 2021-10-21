package com.nightwolf.crm_test

/**
 *
 * create :liwu
 * date:2021/10/21
 * desc: 加载数据UI时的
 */
interface LoadUiInt {

    fun startLoad()

    fun endLoad()

    fun loadError(mes: String)
}