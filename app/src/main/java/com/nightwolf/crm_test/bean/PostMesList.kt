package com.nightwolf.crm_test.bean

class PostMesList {
    var scode: String? = null
    var body: BodyBean? = null

    class BodyBean {
        /**
         * notices : [{"user":{"uid":956977,"name":"韩锐","icon":"","type":0},"id":1289734790201748,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":0,"created":1593483974176,"belongId":""},{"user":{"uid":1066504,"name":"王继浩-养老项目","icon":"","type":0},"id":1289679265808799,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":1,"created":1593480592505,"belongId":""},{"user":{"uid":956971,"name":"于庆龙-养老项目","icon":"","type":0},"id":1289668913496477,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":0,"created":1593479943932,"belongId":""},{"user":{"uid":1069965,"name":"王耀-养老项目","icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/271301/2019/08/16/s_cbbbff92-19aa-4939-aec2-4377a2f35e83.jpg","type":0},"id":1289664863207811,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":0,"created":1593479700121,"belongId":""},{"user":{"uid":1213280295387561,"name":"强睿-养老项目","icon":"","type":0},"id":1289639292092802,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":0,"created":1593478136373,"belongId":""},{"user":{"uid":856582364168428,"name":"何洋洋-守护中心客服","icon":"","type":0},"id":1289618931728743,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"提交了日报【2020-06-30】","operate":1289613169836457,"system":601,"status":1,"created":1593476942972,"belongId":""},{"user":{"uid":856582364168428,"name":"何洋洋-守护中心客服","icon":"","type":0},"id":1289616966467933,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"提交了日报【2020-06-30】","operate":1289613169836457,"system":601,"status":1,"created":1593476815836,"belongId":""},{"user":{"uid":856582364168428,"name":"何洋洋-守护中心客服","icon":"","type":0},"id":1289616205070689,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"提交了日报【2020-06-30】","operate":1289613169836457,"system":601,"status":0,"created":1593476761802,"belongId":""},{"user":{"uid":1065708,"name":"黄林晴-养老项目","icon":"","type":0},"id":1289578354475422,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":1,"created":1593474555568,"belongId":""},{"user":{"uid":766444514427074,"name":"赵晓-养老项目","icon":"","type":0},"id":1289488006447472,"target":1213283083206995,"app":10,"entityName":"","group":{"gid":"","name":"","type":0,"permission":0},"content":"评论了你的工作报告","operate":1289051527119248,"system":601,"status":1,"created":1593469106649,"belongId":""}]
         * hasMore : true
         */
        var hasMore = false
        var notices: List<NoticesBean>? = null

        class NoticesBean {
            /**
             * user : {"uid":956977,"name":"韩锐","icon":"","type":0}
             * id : 1289734790201748
             * target : 1213283083206995
             * app : 10
             * entityName :
             * group : {"gid":"","name":"","type":0,"permission":0}
             * content : 评论了你的工作报告
             * operate : 1289051527119248
             * system : 601
             * status : 0
             * created : 1593483974176
             * belongId :
             */
            var user: UserBean? = null
            var id: Long = 0
            var target: Long = 0
            var app = 0
            var entityName: String? = null
            var group: GroupBean? = null
            var content: String? = null
            var operate: Long = 0
            var system = 0
            var status = 0
            var created: Long = 0
            var belongId: String? = null

            class UserBean {
                /**
                 * uid : 956977
                 * name : 韩锐
                 * icon :
                 * type : 0
                 */
                var uid :Long = 0
                var name: String? = null
                var icon: String? = null
                var type = 0

            }

            class GroupBean {
                /**
                 * gid :
                 * name :
                 * type : 0
                 * permission : 0
                 */
                var gid: String? = null
                var name: String? = null
                var type = 0
                var permission = 0

            }
        }
    }
}