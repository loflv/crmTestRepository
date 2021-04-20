package com.nightwolf.crm_test.api

import com.nightwolf.crm_test.base.OtherReply
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
        @Query("_vs") _vs: String?
    ): PostMesList

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
    suspend fun mainIndex(@Url url: String?): String

    @GET
    suspend fun getLastToken(@Url url: String?): TokenBean

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST
    suspend fun getAuthorizeCode(
        @Url url: String?,
        @Body requestBody: RequestBody?
    ): authorizeCodeBean

    @FormUrlEncoded
    @POST
    suspend fun login(
        @Url url: String?,
        @Field("login_name") login_name: String?,
        @Field("password") password: String?,
        @Field("login_type") login_type: String?
    ): LoginBean

    @GET("comment/list.action")
    suspend fun getOtherReply(
        @Query("systemItemId") systemItemId: String?,
        @Query("systemId") systemId: String?,
        @Query("cache_key") cache_key: String?,
        @Query("_vs") _vs: String?
    ): OtherReply
}