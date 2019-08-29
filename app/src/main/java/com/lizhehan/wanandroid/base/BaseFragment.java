package com.lizhehan.wanandroid.base;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.application.MyApplication;
import com.lizhehan.wanandroid.util.network.NetBroadcastReceiver;
import com.lizhehan.wanandroid.util.network.NetUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView, NetBroadcastReceiver.NetChangeListener {
    /**
     * 处理页面加载中、页面加载失败、页面没数据、离线
     */
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int OFFLINE_STATE = 2;
    private static final int ERROR_STATE = 3;
    private static final int EMPTY_STATE = 4;
    public static NetBroadcastReceiver.NetChangeListener listener;
    protected Activity activity;
    protected MyApplication context;
    private Unbinder unbinder;
    private NetBroadcastReceiver netBroadcastReceiver;
    private int netType;
    private View mErrorView;
    private View mLoadingView;
    private View mOfflineView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private TextView tvMsg;
    /**
     * 当前状态
     */
    private int currentState = NORMAL_STATE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        context = MyApplication.getInstance();
        unbinder = ButterKnife.bind(this, view);
        listener = this;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netBroadcastReceiver = new NetBroadcastReceiver();
        activity.registerReceiver(netBroadcastReceiver, intentFilter);
        initView();
        initData();
        checkNet();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        activity.unregisterReceiver(netBroadcastReceiver);
    }

    /**
     * 获取 布局信息
     *
     * @return
     */
    public abstract int getLayoutResID();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 初始化 ui 布局
     */
    protected void initView() {
        if (getView() == null) {
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(activity, R.layout.view_loading, parent);
        View.inflate(activity, R.layout.view_offline, parent);
        View.inflate(activity, R.layout.view_error, parent);
        View.inflate(activity, R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mOfflineView = parent.findViewById(R.id.offline_group);
        mErrorView = parent.findViewById(R.id.error_group);
        Button retry = mOfflineView.findViewById(R.id.btn_retry);
        Button reLoad = mErrorView.findViewById(R.id.bt_reload);
        mEmptyView = parent.findViewById(R.id.empty_group);
        tvMsg = parent.findViewById(R.id.tv_err_msg);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        reLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mOfflineView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    public void checkNet() {
        if (!isNetConnect()) {
            showOffline();
        }
    }

    public boolean isNetConnect() {
        if (netType == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netType == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netType == NetUtil.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    @Override
    public void onNetChange(int status) {
        if (status == NetUtil.NETWORK_NONE) {
            netType = NetUtil.NETWORK_NONE;
        } else if (status == NetUtil.NETWORK_MOBILE) {
            netType = NetUtil.NETWORK_MOBILE;
            if (currentState == OFFLINE_STATE || currentState == ERROR_STATE) {
                reload();
            }
        } else if (status == NetUtil.NETWORK_WIFI) {
            netType = NetUtil.NETWORK_WIFI;
            if (currentState == OFFLINE_STATE || currentState == ERROR_STATE) {
                reload();
            }
        }
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String err) {
        if (!isNetConnect()) {
            showOffline();
        } else {
            if (currentState == ERROR_STATE) {
                return;
            }
            hideCurrentView();
            currentState = ERROR_STATE;
            mErrorView.setVisibility(View.VISIBLE);
            tvMsg.setText(err);
        }
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOffline() {
        if (currentState == OFFLINE_STATE) {
            return;
        }
        hideCurrentView();
        currentState = OFFLINE_STATE;
        mOfflineView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void reload() {

    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case OFFLINE_STATE:
                mOfflineView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
