package com.lizhehan.wanandroid.base;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lizhehan.wanandroid.application.MyApplication;
import com.lizhehan.wanandroid.util.network.NetBroadcastReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {
    public static NetBroadcastReceiver.NetChangeListener listener;

    protected MyApplication context;
    protected BaseActivity activity;

    private Unbinder unbinder;
    private NetBroadcastReceiver netBroadcastReceiver;
//    private int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        unbinder = ButterKnife.bind(this);
        context = MyApplication.getInstance();
        activity = this;
        listener = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netBroadcastReceiver = new NetBroadcastReceiver();
        registerReceiver(netBroadcastReceiver, intentFilter);
        initStatusColor();
        initToolbar();
        initView();
        initData();
        checkNet();
        login();
    }

    private void initStatusColor() {

    }

    protected abstract int getLayoutResID();

    protected abstract void initView();

    protected abstract void initData();

    protected void checkNet() {
    }

    protected void initToolbar() {
    }

    protected void login() {
    }


//    public boolean isNetConnect() {
//        if (netType == NetUtil.NETWORK_MOBILE) {
//            Toast.makeText(activity,"NETWORK_MOBILE", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (netType == NetUtil.NETWORK_WIFI) {
//            Toast.makeText(activity,"NETWORK_WIFI", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (netType == NetUtil.NETWORK_NONE) {
//            Toast.makeText(activity,"NETWORK_NONE", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        Toast.makeText(activity,"false", Toast.LENGTH_SHORT).show();
//        return false;
//    }

    @Override
    public void onNetChange(int status) {
//        if(status == NetUtil.NETWORK_NONE) {
//            Toast.makeText(activity,"NONE", Toast.LENGTH_SHORT).show();
//            netType = NetUtil.NETWORK_NONE;
//        } else if (status == NetUtil.NETWORK_MOBILE) {
//            Toast.makeText(activity,"MOBILE", Toast.LENGTH_SHORT).show();
//            netType = NetUtil.NETWORK_MOBILE;
//        } else if (status == NetUtil.NETWORK_WIFI) {
//            Toast.makeText(activity,"WIFI", Toast.LENGTH_SHORT).show();
//            netType = NetUtil.NETWORK_WIFI;
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        unregisterReceiver(netBroadcastReceiver);
    }
}
