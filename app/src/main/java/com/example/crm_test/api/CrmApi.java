package com.example.crm_test.api;

import com.example.crm_test.bean.LoginBean;
import com.example.crm_test.bean.PostMesBean;
import com.example.crm_test.bean.PostMesList;
import com.example.crm_test.bean.PostReadResponseBean;
import com.example.crm_test.bean.TokenBean;
import com.example.crm_test.bean.authorizeCodeBean;

import io.reactivex.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CrmApi {

    /**
     * 查看明细
     *
     * @param reportId
     * @param cache_key
     * @param _vs
     * @return
     */
    @GET("workreport/one_n.action")
    abstract Observable<PostMesBean> getRecordDetail(
            @Query("reportId") String reportId,
            @Query("cache_key") String cache_key,
            @Query("_vs") String _vs
    );

    /**
     * 查看消息通知
     *
     * @param cache_key
     * @param noticeId
     * @param _vs
     * @return
     */
    @GET("notice/mineNotice.action")
    abstract Observable<PostMesList> getRecord(
            @Query("cache_key") String cache_key,
            @Query("noticeId") Long noticeId,
            @Query("_vs") String _vs
    );


    /**
     * 发送已阅
     */
    @Multipart
    @POST("comment/add.action")
    abstract Observable<PostReadResponseBean> sendHaveRead(
            @Query("systemItemId") String systemItemId,
            @Query("systemId") String systemId,
            @Query("cache_key") String cache_key,
            @Query("_vs") String vs,
            @Part("content") RequestBody content,
            @Part("source") RequestBody source
    );

    @GET("notice/readRemind.action")
    abstract Observable<String> sendMesRead(
            @Query("source") String source,
            @Query("noticeId") String noticeId,
            @Query("cache_key") String cache_key,
            @Query("_vs") String _vs
    );


    @GET
    Observable<String> mainIndex(@Url String url);

    @GET
    Observable<TokenBean> LoginLast(@Url String url);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST
    Observable<authorizeCodeBean>  getAuthorizeCode(@Url String url,@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST
    abstract Observable<LoginBean> login(
            @Url String url,
            @Field("login_name") String login_name,
            @Field("password") String password,
            @Field("login_type") String login_type
    );



}
