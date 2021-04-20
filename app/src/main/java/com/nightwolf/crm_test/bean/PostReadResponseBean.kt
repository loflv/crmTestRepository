package com.nightwolf.crm_test.bean

class PostReadResponseBean {
    /**
     * scode : 0
     * head : {"uid":1213283083206995,"tid":271301,"isex":false}
     * body : {"isItemOwner":false,"user":{"uid":1213283083206995,"name":"李武-养老项目","icon":"","type":0,"prefix":"李武-养老项目"},"content":"已阅","date":1593566156066,"id":1291080959590826,"timestamp":"","images":[]}
     */
    var scode: String? = null
    var head: HeadBean? = null
    var body: BodyBean? = null

    class HeadBean {
        /**
         * uid : 1213283083206995
         * tid : 271301
         * isex : false
         */
        var uid: Long = 0
        var tid = 0
        var isIsex = false
            private set

        fun setIsex(isex: Boolean) {
            isIsex = isex
        }
    }

    class BodyBean {
        /**
         * isItemOwner : false
         * user : {"uid":1213283083206995,"name":"李武-养老项目","icon":"","type":0,"prefix":"李武-养老项目"}
         * content : 已阅
         * date : 1593566156066
         * id : 1291080959590826
         * timestamp :
         * images : []
         */
        var isIsItemOwner = false
            private set
        var user: UserBean? = null
        var content: String? = null
        var date: Long = 0
        var id: Long = 0
        var timestamp: String? = null
        var images: List<*>? = null

        fun setIsItemOwner(isItemOwner: Boolean) {
            isIsItemOwner = isItemOwner
        }

        class UserBean {
            /**
             * uid : 1213283083206995
             * name : 李武-养老项目
             * icon :
             * type : 0
             * prefix : 李武-养老项目
             */
            var uid: Long = 0
            var name: String? = null
            var icon: String? = null
            var type = 0
            var prefix: String? = null

        }
    }
}