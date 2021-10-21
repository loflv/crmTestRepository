package com.nightwolf.crm_test.bean

class OtherReply {
    var status = 0
    var statusText: String? = null
    var data: DataBean? = null

    class DataBean {
        private val users: Any? = null
        var commentList: List<CommentListBean>? = null

        class CommentListBean {
            var id: Long = 0
            var systemId = 0
            var systemItemId: Long = 0
            var content: String? = null
            var createdAt: Long = 0
            var userId: String? = ""
            var user: UserBean? = null
            var ats: AtsBean? = null
            var atDeparts: AtDepartsBean? = null
            var atTeams: AtTeamsBean? = null
            var images: List<*>? = null

            class UserBean(val id: String, val name: String)
            class AtsBean
            class AtDepartsBean
            class AtTeamsBean

        }
    }
}