package com.nightwolf.crm_test.util

import android.content.Context
import androidx.core.content.edit
import com.nightwolf.crm_test.MyApplication
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

object RetrofitUtils {

    var s_cookie: String? = null
    var ienterprise_passport: String? = null

    fun <T> createRetrofitService(
        clazz: Class<T>?,
        specialUrl: String = "",
        saveCookie: Boolean = false
    ): T {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
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
                        val request = original.newBuilder().build()

                        val proceed = chain.proceed(request)
                        val headers = proceed.headers("Set-Cookie")
                        var cookie = ""
                        for (header in headers) {
                            //提取session的内容
                            val reSub = reSub(header, "SESSION")
                            reSub?.isNotEmpty()?.let {
                                if (it) {
                                    cookie += "SESSION=$reSub;"
                                }
                            }
                            s_cookie = cookie.apply {
                                MyApplication.mContext.getSharedPreferences(
                                    "login",
                                    Context.MODE_PRIVATE
                                ).edit {
                                    putString("Cookie", this@apply)
                                }
                            }
                        }

                        val newBuilder = original.newBuilder()
                        newBuilder.addHeader("Cookie", cookie)
                        newBuilder.addHeader(
                            "User-Agent",
                            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36"
                        )
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
                                .addHeader(
                                    "User-Agent",
                                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36"
                                )
                                .build()
                        return chain.proceed(request)
                    }
                }
            })
            .addInterceptor(interceptor)

        val client = builder.build()
        var url = "https://crm-p05.xiaoshouyi.com/mobile/"
        if (specialUrl.isNotBlank()) {
            url = specialUrl
        }
        val retrofit = Retrofit.Builder().baseUrl(url)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }


    /**
     * 提取key的内容
     */
    private fun reSub(content: String?, key: String): String? {
        val compile = Pattern.compile("(?<=$key=).*?(?=;)")
        val matcher = compile.matcher(content)
        if (matcher.find()) {

            return matcher.group()
        }
        return ""
    }

}