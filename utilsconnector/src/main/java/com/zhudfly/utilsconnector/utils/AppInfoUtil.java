package com.zhudfly.utilsconnector.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.util.List;

/**
 * App info helper
 * Created by zhudf on 2018/5/9.
 */

public class AppInfoUtil {

    /**
     * get App version name
     *
     * @param context an application environment
     * @return app version name
     */
    public static String getVersionName(@NonNull Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return versionName;
    }

    /**
     * get App version code
     *
     * @param context an application environment
     * @return app version code
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return versionCode;
    }

    /**
     * check intent is available or not
     *
     * @param context an application environment
     * @param intent intent to be examined
     * @return true if intent is available
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (context != null && intent != null) {
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } else {
            return false;
        }
    }

    /**
     * get top activity of the app
     *
     * @param context an application environment
     * @return class name of top activity
     */
    public static String getTopActivity(@NonNull Context context) {
        if (context == null) return "";

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) return "";

        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        if (runningTaskInfo != null) {
            return runningTaskInfo.get(0).topActivity.getClassName();
        } else {
            return "";
        }
    }

    /**
     * install apk
     *
     * @param context an application environment
     * @param authority The authority of a {@link FileProvider} defined in a
     *            {@code <provider>} element in your app's manifest.
     * @param pathStr the path of apk file
     */
    public static void install(@NonNull Context context, String authority, String pathStr) {
        File apkFile = new File(pathStr);
        if (context == null || !apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //if system version is same or higher than AndroidN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, authority, apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }


    /**
     * open market
     * @param context an application environment
     * @return true if have one or more market in mobile
     */
    public static boolean openMarket(@NonNull Context context){
        Uri marketUri = Uri.parse("market://details?id=com.android.icredit");
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            if (isIntentAvailable(context, marketIntent)) {
                context.startActivity(marketIntent);
                return true;
            }
            return false;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return false;
        }
    }
}
