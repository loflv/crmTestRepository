package com.nightwolf.crm_test.base

class FeedBean {
    /**
     * scode : 0
     * head : {"uid":1213283083206995,"tid":271301,"isex":false}
     * body : {"hasMore":false,"atList":[{"id":1669801745255729,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"@李武-养老项目 日报中须包含个人BUG部分","date":1616681982093,"system":601,"item":1669791997726028,"id":1669808213085534}},{"id":1477289485255063,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"现在地图用的是高德还是百度？@李武-养老项目","date":1604931405043,"system":601,"item":1477246926668124,"id":1477289617391937}},{"id":1333583724642736,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"日报中需包含个人Bug部分内容@李武-养老项目","date":1596160296003,"system":601,"item":1332927518687597,"id":1333583198912928}},{"id":1252332436341137,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"@李武-养老项目 每项工作内容后尽量加上完成百分比，完成的也写上\u201c完成\u201d或\u201c100%\u201d","date":1591201116966,"system":601,"item":1252183707124077,"id":1252331429757323}}]}
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
         * hasMore : false
         * atList : [{"id":1669801745255729,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"@李武-养老项目 日报中须包含个人BUG部分","date":1616681982093,"system":601,"item":1669791997726028,"id":1669808213085534}},{"id":1477289485255063,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"现在地图用的是高德还是百度？@李武-养老项目","date":1604931405043,"system":601,"item":1477246926668124,"id":1477289617391937}},{"id":1333583724642736,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"日报中需包含个人Bug部分内容@李武-养老项目","date":1596160296003,"system":601,"item":1332927518687597,"id":1333583198912928}},{"id":1252332436341137,"type":1,"status":1,"comment":{"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"@李武-养老项目 每项工作内容后尽量加上完成百分比，完成的也写上\u201c完成\u201d或\u201c100%\u201d","date":1591201116966,"system":601,"item":1252183707124077,"id":1252331429757323}}]
         */
        var isHasMore = false
        var atList: List<AtListBean>? = null

        class AtListBean {
            /**
             * id : 1669801745255729
             * type : 1
             * status : 1
             * comment : {"user":{"uid":956977,"name":"韩锐-软件总监","icon":"","type":0},"ats":[{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}],"content":"@李武-养老项目 日报中须包含个人BUG部分","date":1616681982093,"system":601,"item":1669791997726028,"id":1669808213085534}
             */
            var id: Long = 0
            var type = 0
            var status = 0
            var comment: CommentBean? = null

            class CommentBean {
                /**
                 * user : {"uid":956977,"name":"韩锐-软件总监","icon":"","type":0}
                 * ats : [{"uid":1213283083206995,"prefix":"李武-养老项目","name":"李武-养老项目","type":0}]
                 * content : @李武-养老项目 日报中须包含个人BUG部分
                 * date : 1616681982093
                 * system : 601
                 * item : 1669791997726028
                 * id : 1669808213085534
                 */
                var user: UserBean? =
                    null
                var content: String? = null
                var date: Long = 0
                var system = 0
                var item: Long = 0
                var id: Long = 0
                var ats: List<AtsBean>? = null

                class UserBean {
                    /**
                     * uid : 956977
                     * name : 韩锐-软件总监
                     * icon :
                     * type : 0
                     */
                    var uid = 0
                    var name: String? = null
                    var icon: String? = null
                    var type = 0

                }

                class AtsBean {
                    /**
                     * uid : 1213283083206995
                     * prefix : 李武-养老项目
                     * name : 李武-养老项目
                     * type : 0
                     */
                    var uid: Long = 0
                    var prefix: String? = null
                    var name: String? = null
                    var type = 0

                }
            }
        }
    }
}