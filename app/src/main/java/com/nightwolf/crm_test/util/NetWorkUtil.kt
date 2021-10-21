/*
 * Copyright (C),2007-2020, LonBon Technologies Co. Ltd. All Rights Reserved.
 */

package com.nightwolf.crm_test.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

/**
 * @author huanglinqing
 * @project BasicComponentLibrary
 * @date 2020/8/3
 * @desc 网络相关工具类
 */
object NetWorkUtil {
    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isConnected(context: Context): Boolean {
        val info = getActiveNetworkInfo(context)
        return info?.isConnected ?: false
    }

    /**
     * 获取网络连接信息
     * @param context 上下文
     * @return
     */
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * 判断网络是否是4G
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    fun is4G(context: Context): Boolean {
        val info = getActiveNetworkInfo(context)
        return info != null && info.isAvailable && info.subtype == TelephonyManager.NETWORK_TYPE_LTE
    }

    /**
     * 判断移动数据是否打开
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    fun getDataEnabled(context: Context): Boolean {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
            if (null != getMobileDataEnabledMethod) {
                return getMobileDataEnabledMethod.invoke(tm) as Boolean
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 打开或关闭移动数据
     *
     * 需系统应用 需添加权限`<uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>`
     *
     * @param context 上下文
     * @param enabled `true`: 打开<br></br>`false`: 关闭
     */
    fun setDataEnabled(context: Context, enabled: Boolean) {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val setMobileDataEnabledMethod =
                tm.javaClass.getDeclaredMethod("setDataEnabled", Boolean::class.javaPrimitiveType)
            setMobileDataEnabledMethod?.invoke(tm, enabled)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 判断wifi是否打开
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>`
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    fun getWifiEnabled(context: Context): Boolean {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * 打开或关闭wifi
     *
     * 需添加权限 `<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>`
     *
     * @param context 上下文
     * @param enabled `true`: 打开<br></br>`false`: 关闭
     */
    fun setWifiEnabled(context: Context, enabled: Boolean) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (enabled) {
            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
            }
        } else {
            if (wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = false
            }
        }
    }

    /**
     * 判断wifi是否连接状态
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @param context 上下文
     * @return `true`: 连接<br></br>`false`: 未连接
     */
//    fun isWifiConnected(context: Context): Boolean {
//        val cm = context
//            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
//    }

    /**
     * 获取网络运营商名称
     *
     * 中国移动、如中国联通、中国电信
     *
     * @param context 上下文
     * @return 运营商名称
     */
    fun getNetworkOperatorName(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm?.networkOperatorName
    }

    /**
     * 获取当前网络类型
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @param context 上下文
     * @return 网络类型
     *
     *
     */
    fun getNetworkType(context: Context): NetworkType {
        var netType = NetworkType.NETWORK_NO
        val info = getActiveNetworkInfo(context)
        if (info != null && info.isAvailable) {
            netType = if (info.type == ConnectivityManager.TYPE_WIFI) {
                NetworkType.NETWORK_WIFI
            } else if (info.type == ConnectivityManager.TYPE_MOBILE) {
                when (info.subtype) {
                    TelephonyManager.NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.NETWORK_2G
                    TelephonyManager.NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.NETWORK_3G
                    TelephonyManager.NETWORK_TYPE_IWLAN, TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.NETWORK_4G
                    else -> {
                        val subtypeName = info.subtypeName
                        if (subtypeName.equals("TD-SCDMA", ignoreCase = true)
                            || subtypeName.equals("WCDMA", ignoreCase = true)
                            || subtypeName.equals("CDMA2000", ignoreCase = true)
                        ) {
                            NetworkType.NETWORK_3G
                        } else {
                            NetworkType.NETWORK_UNKNOWN
                        }
                    }
                }
            } else {
                NetworkType.NETWORK_UNKNOWN
            }
        }
        return netType
    }

    /**
     * 获取IP地址
     *
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     *
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    fun getIPAddress(useIPv4: Boolean): String? {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp) {
                    continue
                }
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress = addresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val hostAddress = inetAddress.hostAddress
                        val isIPv4 = hostAddress.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return hostAddress
                        } else {
                            if (!isIPv4) {
                                val index = hostAddress.indexOf('%')
                                return if (index < 0) hostAddress.toUpperCase() else hostAddress.substring(
                                    0,
                                    index
                                ).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取域名ip地址
     *
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     *
     * @param domain 域名
     * @return ip地址
     */
    fun getDomainAddress(domain: String?): String? {
        try {
            val exec = Executors.newCachedThreadPool()
            val fs = exec.submit(Callable {
                val inetAddress: InetAddress
                try {
                    inetAddress = InetAddress.getByName(domain)
                    return@Callable inetAddress.hostAddress
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                }
                null
            })
            return fs.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return null
    }

    enum class NetworkType {
        NETWORK_WIFI, NETWORK_4G, NETWORK_3G, NETWORK_2G, NETWORK_UNKNOWN, NETWORK_NO
    }
}