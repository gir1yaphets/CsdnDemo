package com.example.pengxiaolve.csdndemo.commonUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pengxiaolve on 16/6/19.
 */
public class NetUtil {

    /**
     * 判断当前是否存在网络连接
     * @param context
     * @return
     */
    public static boolean checkNetConn(Context context) {
        boolean result = false;

        if (isWIFIConnected(context) || isMobileConnected(context)) {
            result = true;
        }

        return result;
    }

    /**
     * 判断当前wifi是否连接
     * @param context
     * @return
     */
    public static boolean isWIFIConnected(Context context) {
        boolean result = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null && networkInfo.isConnected()) {
            result = true;
        }

        return result;
    }

    /**
     * 判断当前数据是否连接
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        boolean result = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo != null && networkInfo.isConnected()) {
            result = true;
        }
        return result;
    }
}
