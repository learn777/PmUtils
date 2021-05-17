package com.pp.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class NetworkUtils {
    /**
     * 获取网络类型
     *
     * @param context
     * @return {@link ConnectivityManager#TYPE_MOBILE} {@link NetworkCapabilities.TRANSPORT_CELLULAR},
     * {@link ConnectivityManager#TYPE_WIFI} {@link NetworkCapabilities.TRANSPORT_WIFI}
     */
    public static int getType(@NonNull Context context) {
        ConnectivityManager conMann = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMann != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                NetworkInfo networkInfo = conMann.getActiveNetworkInfo();
                switch (Objects.requireNonNull(networkInfo).getType()) {
                    case ConnectivityManager.TYPE_MOBILE:
                        return ConnectivityManager.TYPE_MOBILE;
                    case ConnectivityManager.TYPE_WIFI:
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
                        int ipAddress = wifiInfo.getIpAddress();
                        return ConnectivityManager.TYPE_WIFI;
                }
            } else {
                Network network = conMann.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities nc = conMann.getNetworkCapabilities(network);
                    if (nc != null) {
                        if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {//WIFI
                            return NetworkCapabilities.TRANSPORT_WIFI;
                        } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {//移动数据
                            return NetworkCapabilities.TRANSPORT_CELLULAR;
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 获取IP地址
     *
     * @param context
     * @return
     */
    public static @Nullable
    String getIP(@NonNull Context context) {
        switch (getType(context)) {
            case ConnectivityManager.TYPE_MOBILE:
                return getLocalIpAddress();
            case ConnectivityManager.TYPE_WIFI:
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                return intToIp(ipAddress);
            default:
                return null;
        }
    }

    /**
     * 如果连接的是移动网络，第二步，获取本地ip地址：getLocalIpAddress();这样获取的是ipv4格式的ip地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            String ipv4;
            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && isIPv4Address(ipv4 = address.getHostAddress())) {
                        return ipv4;
                    }
                }

            }
        } catch (SocketException ex) {
            Log.e("localIp", ex.toString());
        }
        return null;
    }

    private static boolean isIPv4Address(String ip) {
        return ip.contains(".");
    }

    /**
     * 如果连接的是WI-FI网络，第三步，获取WI-FI ip地址：intToIp(ipAddress);
     * @param ipInt
     * @return
     */
    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
}
