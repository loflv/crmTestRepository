package com.example.crm_test.bean



class LoginBean {
    /**
     * result : {"tenant_list":[{"company":"来邦科技","user_type":"0","id":"271301","encryptionKey":"a947946c39de505676c92ae321c6a0d6"}],"passport_id":1213273632457119}
     * scode : 200
     */
    var result: ResultBean? = null
    var scode: String? = null
    val error_msg:String? = null

    class ResultBean {
        /**
         * tenant_list : [{"company":"来邦科技","user_type":"0","id":"271301","encryptionKey":"a947946c39de505676c92ae321c6a0d6"}]
         * passport_id : 1213273632457119
         */
        var passport_id: Long = 0
        var tenant_list: List<TenantListBean>? = null

        class TenantListBean {


            /**
             * company : 来邦科技
             * user_type : 0
             * id : 271301
             * encryptionKey : a947946c39de505676c92ae321c6a0d6
             */
            var company: String? = null
            var user_type: String? = null
            var id: String? = null
            var encryptionKey: String? = null

        }
    }
}