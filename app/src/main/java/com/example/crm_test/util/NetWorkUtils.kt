package com.example.crm_test.util

import android.content.Context
import androidx.core.content.edit
import com.example.crm_test.MyApplication
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object NetWorkUtils {

    var s_cookie: String? = null
    var ienterprise_passport: String? = null

    fun <T> createRetrofitService(clazz: Class<T>?, saveCookie: Boolean): T {
        val interceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Logger.d(message)
                }
            })

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    if (saveCookie) {
                        val request =
                            original.newBuilder()
                                .build()

                        val proceed = chain.proceed(request)
                        val headers = proceed.headers("Set-Cookie")
                        var cookie = ""
                        for (header in headers) {
                            val reSub = reSub(header, "SESSION")
                            reSub?.isNotEmpty()?.let {
                                if (it) {
                                    cookie += "SESSION=$reSub;"
                                }
                            }
                            s_cookie = cookie

                        }
                        MyApplication.mContext.getSharedPreferences(
                            "login",
                            Context.MODE_PRIVATE
                        ).edit {
                            putString("Cookie", s_cookie)
                        }

                        val newBuilder = original.newBuilder()
                        newBuilder.addHeader("Cookie", cookie)
                        newBuilder.addHeader("User-Agent", "PostmanRuntime/7.24.1")

                        return chain.proceed(newBuilder.build())
                    } else {
                        if (s_cookie == null) {
                            s_cookie = MyApplication.mContext.getSharedPreferences(
                                "login",
                                Context.MODE_PRIVATE
                            ).getString("Cookie", "")!!
                        }

                        if (ienterprise_passport.isNullOrBlank()) {
                            ienterprise_passport = MyApplication.mContext.getSharedPreferences(
                                "login",
                                Context.MODE_PRIVATE
                            ).getString("x-ienterprise-passport", "")!!
                        }

                        val request =
                            original.newBuilder()
                                .addHeader("cookie", s_cookie!!)
                                .addHeader(
                                    "Cookie",
                                    "x-ienterprise-passport=${ienterprise_passport};"
                                )
                                .addHeader("User-Agent", "PostmanRuntime/7.24.1")
                                .build()
                        return chain.proceed(request)
                    }

                }
            })
            .addInterceptor(interceptor)
        val client = builder.build()
        val retrofit = Retrofit.Builder().baseUrl("https://crm.xiaoshouyi.com/auc/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }


    fun <T> phoneRetrofitService(clazz: Class<T>?): T {
        val interceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Logger.d(message)
                }
            })

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    if (ienterprise_passport.isNullOrBlank()) {
                        ienterprise_passport = MyApplication.mContext.getSharedPreferences(
                            "login",
                            Context.MODE_PRIVATE
                        ).getString("x-ienterprise-passport", "")!!
                    }

                    if (s_cookie.isNullOrBlank()) {
                        s_cookie = MyApplication.mContext.getSharedPreferences(
                            "login",
                            Context.MODE_PRIVATE
                        ).getString("cookie", "")!!
                    }

                    val request =
                        original.newBuilder()
                            .addHeader("cookie", s_cookie!!)
                            .addHeader(
                                "cookie",
                                "x-ienterprise-passport=${ienterprise_passport};"
                            )
                            .addHeader("User-Agent", "PostmanRuntime/7.24.1")
                            .build()
                    return chain.proceed(request)
                }

            })
            .addInterceptor(interceptor)
        val client = builder.build()
        val retrofit = Retrofit.Builder().baseUrl("https://crm.xiaoshouyi.com/mobile/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }


    fun reSub(content: String?, key: String): String? {
        val compile = Pattern.compile("(?<=$key=).*?(?=;)")
        val matcher = compile.matcher(content)
        if (matcher.find()) {

            return matcher.group()
        }
        return ""
    }

}