package com.zhudfly.utilsconnector.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;

/**
 * Device info helper
 * Created by zhudf on 2018/5/2.
 */

public class DeviceInfoUtil {


    // if get the wrong mac address, system (after 6.0) will return '02:00:00:00:00:00'
    // not the real mac address
    private static final String INVALID_MAC_ADDRESS = "02:00:00:00:00:00";

    /**
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />},
     * {@code <uses-permission android:name="android.permission.INTERNET" />}
     *
     * @return the MAC address
     */
    @RequiresPermission(allOf = { ACCESS_WIFI_STATE, INTERNET })
    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByWifiInfo(context);
        if (!INVALID_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!INVALID_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByInetAddress();
        if (!INVALID_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    @SuppressLint({ "HardwareIds", "MissingPermission" })
    private static String getMacAddressByWifiInfo(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return INVALID_MAC_ADDRESS;
    }

    private static String getMacAddressByNetworkInterface() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni == null || !ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : macBytes) {
                        sb.append(String.format("%02x:", b));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return INVALID_MAC_ADDRESS;
    }

    private static String getMacAddressByInetAddress() {
        try {
            InetAddress inetAddress = getInterfaceAddress();
            if (inetAddress != null) {
                NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);
                if (ni != null) {
                    byte[] macBytes = ni.getHardwareAddress();
                    if (macBytes != null && macBytes.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b : macBytes) {
                            sb.append(String.format("%02x:", b));
                        }
                        return sb.substring(0, sb.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return INVALID_MAC_ADDRESS;
    }

    private static InetAddress getInterfaceAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp()) continue;
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (hostAddress.indexOf(':') < 0) return inetAddress;
                    }
                }
            }
        } catch (SocketException e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    // -------------------- 获得IP地址 --------------------
    private static final String LOCAL_IP_ADDRESS = "127.0.0.1";

    /**
     *  Get IP Address (not IPV6)
     * @return IP Address
     */
    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp()) continue;
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (isIPv4) {
                            return hostAddress;
                        } else {
                            int index = hostAddress.indexOf('%');
                            return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index)
                                .toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            LogUtil.printStackTrace(e);
        }
        return LOCAL_IP_ADDRESS;
    }


    /**
     * System Version Code
     */
    public static int getSystemVersionCode(){
        return Build.VERSION.SDK_INT;
    }


    /**
     * System Version Release
     */
    public static String getSystemVersionRelease(){
        return Build.VERSION.RELEASE;
    }

    /**
     * @return Device Id
     */
    public static String getDeviceId(){
        return "";
    }
}
