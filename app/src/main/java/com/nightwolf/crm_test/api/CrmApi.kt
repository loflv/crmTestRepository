package com.nightwolf.crm_test.api

import com.nightwolf.crm_test.bean.*
import okhttp3.RequestBody
import retrofit2.http.*

interface CrmApi {
    /**
     * 查看明细
     *
     * @param reportId
     * @param cache_key
     * @param _vs
     * @return
     */
    @GET("workreport/one_n.action")
    suspend fun getRecordDetail(
        @Query("reportId") reportId: String?,
        @Query("cache_key") cache_key: String?,
        @Query("_vs") _vs: String?
    ): PostMesBean

    /**
     * 查看消息通知
     *
     * @param cache_key
     * @param noticeId
     * @param _vs
     * @return
     */
    @GET("notice/mineNotice.action")
    suspend fun getRecord(
        @Query("cache_key") cache_key: String?,
        @Query("noticeId") noticeId: Long?,
        @Query("_vs") _vs: String?,
        @Query("unread") unread: Int
    ): PostMesList

    @GET("workreport/list-my-submitted.action")
    suspend fun getMyRecord(
        @Query("cache_key") cache_key: String?,
        @Query("_vs") _vs: String?,
        @Query("sortorder") sortorder: String,
        @Query("sortdatafield") sortdatafield: String,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): MyReportBean

    /**
     * 发送已阅
     */
    @Multipart
    @POST("comment/add.action")
    suspend fun sendHaveRead(
        @Query("systemItemId") systemItemId: String?,
        @Query("systemId") systemId: String?,
        @Query("cache_key") cache_key: String?,
        @Query("_vs") vs: String?,
        @Part("content") content: RequestBody?,
        @Part("source") source: RequestBody?
    ): PostReadResponseBean

    @GET("notice/readRemind.action")
    suspend fun sendMesRead(
        @Query("source") source: String?,
        @Query("noticeId") noticeId: String?,
        @Query("cache_key") cache_key: String?,
        @Query("_vs") _vs: String?
    ): String

    @GET
    suspend fun getKey(@Url url: String): PasswordKeyBean

    @GET
    suspend fun mainIndex(@Url url: String?): String

    @GET
    suspend fun getLastToken(@Url url: String?): TokenBean

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST
    suspend fun getAuthorizeCode(
        @Url url: String?,
        @Body requestBody: RequestBody?
    ): AuthorizeCodeBean

    @FormUrlEncoded
    @POST
    suspend fun login(
        @Url url: String?,
        @Field("login_name") login_name: String?,
        @Field("password") password: String?,
        @Field("login_type") login_type: String?
    ): LoginBean

    @GET("json/sns_comment/list.action")
    suspend fun getOtherReply(
        @Query("commentSystemItemId") systemId: String?,
        @Query("commentSystemId") systemItemId: String?,
        @Query("cache_key") cache_key: String?,
        @Query("pageSize") pageSize: Int,
        @Query("pageNo") pageNo: Int?,
        @Query("_vs") _vs: String?
    ): OtherReply


    @GET("feed/ats.action")
    suspend fun getFeed(
        @Query("size") size: Int?,
        @Query("page") page: Int?,
        @Query("unread") unread: Int?,
        @Query("cache_key") cache_key: String?,
        @Query("_vs") _vs: String?
    ): FeedBean
}