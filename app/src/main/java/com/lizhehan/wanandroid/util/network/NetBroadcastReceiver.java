package com.lizhehan.wanandroid.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.base.BaseFragment;

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetChangeListener activityListener = BaseActivity.listener;
    public NetChangeListener fragmentListener = BaseFragment.listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            // 如果相等的话就说明网络状态发生了变化
            int netWorkState = NetUtil.getNetWorkState(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (activityListener != null) {
                activityListener.onNetChange(netWorkState);
            }
            if (fragmentListener != null) {
                fragmentListener.onNetChange(netWorkState);
            }
        }
    }


    public interface NetChangeListener {
        void onNetChange(int status);
    }
}
