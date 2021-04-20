package com.nightwolf.crm_test.bean

class TokenBean {
    /**
     * scode : 200
     * error_msg :
     * result : {"tokenData":{"access_token":"43e43c458d2885b1f5f5820a6eda2e902a890ddf13ccad3c4a8296fd195f2fe2.MjcxMzAx","token_type":"Bearer","refresh_token":"02b13c1c-1ce4-476e-95a5-3694db1f6c1e","expires_in":863999,"scope":"all","tenant_id":"271301","refresh_token_expires_in":2592000,"login_type":"mobile","encryption_key":"a947946c39de505676c92ae321c6a0d6","client_id":"0f9ea81872aa08da71a5f4cd31d54887","passport_id":"1213273632457119","mobile_params":"{\"os\":\"28\",\"model\":\"HUAWEI HWJKM-H\",\"source\":\"1\",\"imei\":\"865472047803957\",\"bundleId\":\"null\",\"_vs\":\"2010.7\",\"appType\":\"0\"}","instance_uri":"https://crm.xiaoshouyi.com/index.action"},"mobileAuthData":{"scode":"200","result":{"tenantId":271301,"token":"clPJnjUOl53gj2XbXLUa6Wj7+VkmuKGFv427l7+Vd/dsQ7Air/qJrn6NPjhVbfD7wFHoe5sLS9i8w6beQdCCdq7t2fxgw+HSpSh0+7THIGIIWf3PXvSfotuWT5KAsS81Ou4fSxTc4Ec=","userId":1213283083206995,"isex":false,"companyName":"来邦科技","userName":"李武-养老项目","userType":0,"icon":"","serverTime":1594051388965,"language":1,"languageCode":"zh","isTrialTenant":false,"isColleagueListDownload":true,"isPartnerUser":0,"isActivityRecordForCustomize":true,"isAgent":"0","agentStatus":"0","isHandleField":"0","prmViewUserId":"-1","passport":"18520811326","mainColor":"#FFFFFF","secondColor":"#4E80F5","logo":"https://devrs.s3.cn-north-1.amazonaws.com.cn/34813/2016/12/12/55ea7fd1-5c34-476b-b461-b225400d0fce.png","slogan":"重塑企业与客户的连接","sloganColor":"#FFFFFF","bgColor":"#55ACEE","bgImage":"","bgType":"0","slogan_resourcekey":"brandDesign.1220703631770029"}}}
     */
    var scode: String? = null
    var error_msg: String? = null
    var result: ResultBeanX? = null

    class ResultBeanX {
        /**
         * tokenData : {"access_token":"43e43c458d2885b1f5f5820a6eda2e902a890ddf13ccad3c4a8296fd195f2fe2.MjcxMzAx","token_type":"Bearer","refresh_token":"02b13c1c-1ce4-476e-95a5-3694db1f6c1e","expires_in":863999,"scope":"all","tenant_id":"271301","refresh_token_expires_in":2592000,"login_type":"mobile","encryption_key":"a947946c39de505676c92ae321c6a0d6","client_id":"0f9ea81872aa08da71a5f4cd31d54887","passport_id":"1213273632457119","mobile_params":"{\"os\":\"28\",\"model\":\"HUAWEI HWJKM-H\",\"source\":\"1\",\"imei\":\"865472047803957\",\"bundleId\":\"null\",\"_vs\":\"2010.7\",\"appType\":\"0\"}","instance_uri":"https://crm.xiaoshouyi.com/index.action"}
         * mobileAuthData : {"scode":"200","result":{"tenantId":271301,"token":"clPJnjUOl53gj2XbXLUa6Wj7+VkmuKGFv427l7+Vd/dsQ7Air/qJrn6NPjhVbfD7wFHoe5sLS9i8w6beQdCCdq7t2fxgw+HSpSh0+7THIGIIWf3PXvSfotuWT5KAsS81Ou4fSxTc4Ec=","userId":1213283083206995,"isex":false,"companyName":"来邦科技","userName":"李武-养老项目","userType":0,"icon":"","serverTime":1594051388965,"language":1,"languageCode":"zh","isTrialTenant":false,"isColleagueListDownload":true,"isPartnerUser":0,"isActivityRecordForCustomize":true,"isAgent":"0","agentStatus":"0","isHandleField":"0","prmViewUserId":"-1","passport":"18520811326","mainColor":"#FFFFFF","secondColor":"#4E80F5","logo":"https://devrs.s3.cn-north-1.amazonaws.com.cn/34813/2016/12/12/55ea7fd1-5c34-476b-b461-b225400d0fce.png","slogan":"重塑企业与客户的连接","sloganColor":"#FFFFFF","bgColor":"#55ACEE","bgImage":"","bgType":"0","slogan_resourcekey":"brandDesign.1220703631770029"}}
         */
        var tokenData: TokenDataBean? = null
        var mobileAuthData: MobileAuthDataBean? = null

        class TokenDataBean {
            /**
             * access_token : 43e43c458d2885b1f5f5820a6eda2e902a890ddf13ccad3c4a8296fd195f2fe2.MjcxMzAx
             * token_type : Bearer
             * refresh_token : 02b13c1c-1ce4-476e-95a5-3694db1f6c1e
             * expires_in : 863999
             * scope : all
             * tenant_id : 271301
             * refresh_token_expires_in : 2592000
             * login_type : mobile
             * encryption_key : a947946c39de505676c92ae321c6a0d6
             * client_id : 0f9ea81872aa08da71a5f4cd31d54887
             * passport_id : 1213273632457119
             * mobile_params : {"os":"28","model":"HUAWEI HWJKM-H","source":"1","imei":"865472047803957","bundleId":"null","_vs":"2010.7","appType":"0"}
             * instance_uri : https://crm.xiaoshouyi.com/index.action
             */
            var access_token: String? = null
            var token_type: String? = null
            var refresh_token: String? = null
            var expires_in = 0
            var scope: String? = null
            var tenant_id: String? = null
            var refresh_token_expires_in = 0
            var login_type: String? = null
            var encryption_key: String? = null
            var client_id: String? = null
            var passport_id: String? = null
            var mobile_params: String? = null
            var instance_uri: String? = null

        }

        class MobileAuthDataBean {
            /**
             * scode : 200
             * result : {"tenantId":271301,"token":"clPJnjUOl53gj2XbXLUa6Wj7+VkmuKGFv427l7+Vd/dsQ7Air/qJrn6NPjhVbfD7wFHoe5sLS9i8w6beQdCCdq7t2fxgw+HSpSh0+7THIGIIWf3PXvSfotuWT5KAsS81Ou4fSxTc4Ec=","userId":1213283083206995,"isex":false,"companyName":"来邦科技","userName":"李武-养老项目","userType":0,"icon":"","serverTime":1594051388965,"language":1,"languageCode":"zh","isTrialTenant":false,"isColleagueListDownload":true,"isPartnerUser":0,"isActivityRecordForCustomize":true,"isAgent":"0","agentStatus":"0","isHandleField":"0","prmViewUserId":"-1","passport":"18520811326","mainColor":"#FFFFFF","secondColor":"#4E80F5","logo":"https://devrs.s3.cn-north-1.amazonaws.com.cn/34813/2016/12/12/55ea7fd1-5c34-476b-b461-b225400d0fce.png","slogan":"重塑企业与客户的连接","sloganColor":"#FFFFFF","bgColor":"#55ACEE","bgImage":"","bgType":"0","slogan_resourcekey":"brandDesign.1220703631770029"}
             */
            var scode: String? = null
            var result: ResultBean? =
                null

            class ResultBean {
                /**
                 * tenantId : 271301
                 * token : clPJnjUOl53gj2XbXLUa6Wj7+VkmuKGFv427l7+Vd/dsQ7Air/qJrn6NPjhVbfD7wFHoe5sLS9i8w6beQdCCdq7t2fxgw+HSpSh0+7THIGIIWf3PXvSfotuWT5KAsS81Ou4fSxTc4Ec=
                 * userId : 1213283083206995
                 * isex : false
                 * companyName : 来邦科技
                 * userName : 李武-养老项目
                 * userType : 0
                 * icon :
                 * serverTime : 1594051388965
                 * language : 1
                 * languageCode : zh
                 * isTrialTenant : false
                 * isColleagueListDownload : true
                 * isPartnerUser : 0
                 * isActivityRecordForCustomize : true
                 * isAgent : 0
                 * agentStatus : 0
                 * isHandleField : 0
                 * prmViewUserId : -1
                 * passport : 18520811326
                 * mainColor : #FFFFFF
                 * secondColor : #4E80F5
                 * logo : https://devrs.s3.cn-north-1.amazonaws.com.cn/34813/2016/12/12/55ea7fd1-5c34-476b-b461-b225400d0fce.png
                 * slogan : 重塑企业与客户的连接
                 * sloganColor : #FFFFFF
                 * bgColor : #55ACEE
                 * bgImage :
                 * bgType : 0
                 * slogan_resourcekey : brandDesign.1220703631770029
                 */
                var tenantId = 0
                var token: String? = null
                var userId: Long = 0
                var isIsex = false
                    private set
                var companyName: String? = null
                var userName: String? = null
                var userType = 0
                var icon: String? = null
                var serverTime: Long = 0
                var language = 0
                var languageCode: String? = null
                var isIsTrialTenant = false
                    private set
                var isIsColleagueListDownload = false
                    private set
                var isPartnerUser = 0
                var isIsActivityRecordForCustomize = false
                    private set
                var isAgent: String? = null
                var agentStatus: String? = null
                var isHandleField: String? = null
                var prmViewUserId: String? = null
                var passport: String? = null
                var mainColor: String? = null
                var secondColor: String? = null
                var logo: String? = null
                var slogan: String? = null
                var sloganColor: String? = null
                var bgColor: String? = null
                var bgImage: String? = null
                var bgType: String? = null
                var slogan_resourcekey: String? = null

                fun setIsex(isex: Boolean) {
                    isIsex = isex
                }

                fun setIsTrialTenant(isTrialTenant: Boolean) {
                    isIsTrialTenant = isTrialTenant
                }

                fun setIsColleagueListDownload(isColleagueListDownload: Boolean) {
                    isIsColleagueListDownload = isColleagueListDownload
                }

                fun setIsActivityRecordForCustomize(isActivityRecordForCustomize: Boolean) {
                    isIsActivityRecordForCustomize = isActivityRecordForCustomize
                }

            }
        }
    }
}