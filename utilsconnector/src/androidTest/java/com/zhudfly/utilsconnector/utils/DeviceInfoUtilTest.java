package com.zhudfly.utilsconnector.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhudf on 2018/5/2.
 */
public class DeviceInfoUtilTest {
    @Test
    public void testGetMacAddress() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.e("DeviceInfoUtilTest",DeviceInfoUtil.getMacAddress(appContext));
        Assert.assertEquals("AAAAA",DeviceInfoUtil.getMacAddress(appContext));
    }

    @Test
    public void testGetIPAddress() throws Exception {
    }

    @Test
    public void testGetSystemVersionCode() throws Exception {
        Log.e("DeviceInfoUtilTest",String.valueOf(DeviceInfoUtil.getSystemVersionCode()));
    }

    @Test
    public void testGetSystemVersionRelease() throws Exception {
        Log.e("DeviceInfoUtilTest",DeviceInfoUtil.getSystemVersionRelease());
    }

    @Test
    public void testGetDeviceId() throws Exception {
    }
}