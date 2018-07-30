package com.zhudfly.utilsconnector.utils;

import org.junit.Test;

import static com.zhudfly.utilsconnector.utils.DeviceInfoUtil.getMacAddress;
import static org.junit.Assert.*;

/**
 * Created by zhudf on 2018/5/2.
 */
public class DeviceInfoUtilTest {
    @Test
    public void testGetMacAddress() throws Exception {
        assertEquals("02:00:00:00:00:00",getMacAddress(null));
    }

}