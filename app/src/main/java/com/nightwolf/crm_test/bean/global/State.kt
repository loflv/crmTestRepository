package com.nightwolf.crm_test.bean.global

/**
 *
 * create :liwu
 * date:2021/10/21
 * desc:
 */
sealed class State

object LoadState : State()

object SuccessState : State()

class ErrorState(val errorMsg: String?) : State()