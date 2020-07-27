package com.example.crm_test.base

class OtherReply {
    /**
     * scode : 0
     * head : {"uid":1213283083206995,"tid":271301,"isex":false}
     * body : {"isItemOwner":false,"totalCount":20,"comments":[{"user":{"uid":956973,"name":"王庆","icon":"","type":0,"prefix":"王庆","post":"产品运营中心总监"},"content":"已阅","id":1326915662070152,"date":1595753320870,"timestamp":"","images":[]},{"user":{"uid":1182596423385474,"name":"汪希祥-养老产品经理","icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/271301/2020/04/30/s_b33d84f0-0264-4988-8d15-68eed514a937.jpg","type":0,"prefix":"汪希祥-养老产品经理","post":"养老产品经理"},"content":"已阅","id":1324073832792429,"date":1595579874628,"timestamp":"","images":[]},{"user":{"uid":1216594755420491,"name":"田礼-养老项目","icon":"","type":0,"prefix":"田礼-养老项目","post":"研发人员"},"content":"已阅","id":1324068195664219,"date":1595579521737,"timestamp":"","images":[]},{"user":{"uid":961888,"name":"刘天皓-养老项目","icon":"","type":0,"prefix":"刘天皓-养老项目","post":"研发人员"},"content":"已阅","id":1324063867928899,"date":1595579247938,"timestamp":"","images":[]},{"user":{"uid":957113,"name":"刘闵","icon":"","type":0,"prefix":"刘闵","post":"研发人员"},"content":"已阅","id":1323864095261070,"date":1595567078214,"timestamp":"","images":[]},{"user":{"uid":960452,"name":"曹文健-养老项目","icon":"","type":0,"prefix":"曹文健-养老项目","post":"研发人员"},"content":"已阅","id":1323699921043789,"date":1595557041551,"timestamp":"","images":[]},{"user":{"uid":1069080,"name":"郭子玉-养老测试副经理","icon":"","type":0,"prefix":"郭子玉-养老测试副经理","post":""},"content":"已阅","id":1323646603526478,"date":1595553788263,"timestamp":"","images":[]},{"user":{"uid":1069965,"name":"王耀-养老项目","icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/271301/2019/08/16/s_cbbbff92-19aa-4939-aec2-4377a2f35e83.jpg","type":0,"prefix":"王耀-养老项目","post":"研发人员"},"content":"已阅","id":1323628367069587,"date":1595552680935,"timestamp":"","images":[]},{"user":{"uid":1213280295387561,"name":"强睿-养老项目","icon":"","type":0,"prefix":"强睿-养老项目","post":"研发人员"},"content":"已阅","id":1323627730207112,"date":1595552634470,"timestamp":"","images":[]},{"user":{"uid":1065708,"name":"黄林晴-养老项目","icon":"","type":0,"prefix":"黄林晴-养老项目","post":"研发人员"},"content":"已阅","id":1323622176129351,"date":1595552290521,"timestamp":"","images":[]}]}
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
         * totalCount : 20
         * comments : [{"user":{"uid":956973,"name":"王庆","icon":"","type":0,"prefix":"王庆","post":"产品运营中心总监"},"content":"已阅","id":1326915662070152,"date":1595753320870,"timestamp":"","images":[]},{"user":{"uid":1182596423385474,"name":"汪希祥-养老产品经理","icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/271301/2020/04/30/s_b33d84f0-0264-4988-8d15-68eed514a937.jpg","type":0,"prefix":"汪希祥-养老产品经理","post":"养老产品经理"},"content":"已阅","id":1324073832792429,"date":1595579874628,"timestamp":"","images":[]},{"user":{"uid":1216594755420491,"name":"田礼-养老项目","icon":"","type":0,"prefix":"田礼-养老项目","post":"研发人员"},"content":"已阅","id":1324068195664219,"date":1595579521737,"timestamp":"","images":[]},{"user":{"uid":961888,"name":"刘天皓-养老项目","icon":"","type":0,"prefix":"刘天皓-养老项目","post":"研发人员"},"content":"已阅","id":1324063867928899,"date":1595579247938,"timestamp":"","images":[]},{"user":{"uid":957113,"name":"刘闵","icon":"","type":0,"prefix":"刘闵","post":"研发人员"},"content":"已阅","id":1323864095261070,"date":1595567078214,"timestamp":"","images":[]},{"user":{"uid":960452,"name":"曹文健-养老项目","icon":"","type":0,"prefix":"曹文健-养老项目","post":"研发人员"},"content":"已阅","id":1323699921043789,"date":1595557041551,"timestamp":"","images":[]},{"user":{"uid":1069080,"name":"郭子玉-养老测试副经理","icon":"","type":0,"prefix":"郭子玉-养老测试副经理","post":""},"content":"已阅","id":1323646603526478,"date":1595553788263,"timestamp":"","images":[]},{"user":{"uid":1069965,"name":"王耀-养老项目","icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/271301/2019/08/16/s_cbbbff92-19aa-4939-aec2-4377a2f35e83.jpg","type":0,"prefix":"王耀-养老项目","post":"研发人员"},"content":"已阅","id":1323628367069587,"date":1595552680935,"timestamp":"","images":[]},{"user":{"uid":1213280295387561,"name":"强睿-养老项目","icon":"","type":0,"prefix":"强睿-养老项目","post":"研发人员"},"content":"已阅","id":1323627730207112,"date":1595552634470,"timestamp":"","images":[]},{"user":{"uid":1065708,"name":"黄林晴-养老项目","icon":"","type":0,"prefix":"黄林晴-养老项目","post":"研发人员"},"content":"已阅","id":1323622176129351,"date":1595552290521,"timestamp":"","images":[]}]
         */
        var isIsItemOwner = false
            private set
        var totalCount = 0
        var comments: List<CommentsBean>? = null

        fun setIsItemOwner(isItemOwner: Boolean) {
            isIsItemOwner = isItemOwner
        }

        class CommentsBean {
            /**
             * user : {"uid":956973,"name":"王庆","icon":"","type":0,"prefix":"王庆","post":"产品运营中心总监"}
             * content : 已阅
             * id : 1326915662070152
             * date : 1595753320870
             * timestamp :
             * images : []
             */
            var user: UserBean? =
                null
            var content: String? = null
            var id: Long = 0
            var date: Long = 0
            var timestamp: String? = null
            var images: List<*>? = null

            class UserBean {
                /**
                 * uid : 956973
                 * name : 王庆
                 * icon :
                 * type : 0
                 * prefix : 王庆
                 * post : 产品运营中心总监
                 */
                var uid = 0
                var name: String? = null
                var icon: String? = null
                var type = 0
                var prefix: String? = null
                var post: String? = null

            }
        }
    }
}