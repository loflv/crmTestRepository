package com.nightwolf.crm_test.bean

/**
 * 获取加密key
 */
class PasswordKeyBean {
    var scode: String? = null
    var result: ResultBean? = null
    var error_msg: Any? = null

    class ResultBean {
        var pwdSwitch = 0
        var key: String? = null
    }
}