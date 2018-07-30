package com.zhudfly.connector.UtilTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhudfly.connector.R;
import com.zhudfly.utilsconnector.utils.DeviceInfoUtil;

public class UtilDeviceInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_device_info);

        initView();
    }

    private void initView() {
        TextView tvMac = findViewById(R.id.tvMac);
        TextView tvIP = findViewById(R.id.tvIP);
        TextView tvVersionCode = findViewById(R.id.tvVersionCode);
        TextView tvVersionRelease = findViewById(R.id.tvVersionRelease);

        tvMac.setText(DeviceInfoUtil.getMacAddress(this));
        tvIP.setText(DeviceInfoUtil.getIPAddress());
        tvVersionCode.setText(String.valueOf(DeviceInfoUtil.getSystemVersionCode()));
        tvVersionRelease.setText(DeviceInfoUtil.getSystemVersionRelease());
    }
}
