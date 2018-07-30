package com.zhudfly.utilsconnector.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * 与屏幕分辨率有关的辅助类
 * Created by zhudf on 2018/5/3.
 */

public class ResolutionUtil {

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return window width
     */
    public static int getWindowWidth(@NonNull Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return window height
     */
    public static int getWindowHeight(@NonNull Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * sp 转 px
     *
     * @param context 上下文
     * @param spValue 需要转换的sp值
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp 转 px
     *
     * @param context 上下文
     * @param dpVal 需要转换的dp值
     * @return 转换后的px值
     */
    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
            .getDisplayMetrics());
    }

    /**
     * px 转 dp
     *
     * @param context 上下文
     * @param pxValue 需要转换的px值
     * @return 转换后的dp值
     */
    public static int px2dp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context 上下文
     * @param pxValue 需要转换的px值
     * @return 转换后的sp值
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得控件的高度
     *
     * @param view 需要获取高度的控件
     * @return view‘s height
     */
    public static int getMeasureHeight(@NonNull View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);

        return view.getMeasuredHeight();
    }

    /**
     * 获得控件的宽度
     *
     * @param view 需要获取宽度的控件
     * @return view‘s width
     */
    public static int getMeasureWidth(@NonNull View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);

        return view.getMeasuredWidth();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     * @return status bar’s height
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = context.getResources()
            .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
