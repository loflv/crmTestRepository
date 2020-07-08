package com.example.crm_test.bean

class authorizeCodeBean{
    /**
     * scode : 302
     * result : {"redirectUrl":"https://crm.xiaoshouyi.com/bff/oauth/gettoken?code=sr6esC","type":1}
     * error_msg : null
     */
    var scode: String? = null
    var result: ResultBean? = null
    var error_msg: Any? = null

    class ResultBean {
        /**
         * redirectUrl : https://crm.xiaoshouyi.com/bff/oauth/gettoken?code=sr6esC
         * type : 1
         */
        var redirectUrl: String? = null
        var type = 0

    }
}