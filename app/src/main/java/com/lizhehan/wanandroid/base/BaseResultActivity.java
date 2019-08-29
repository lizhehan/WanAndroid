package com.lizhehan.wanandroid.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.util.network.NetUtil;

public abstract class BaseResultActivity extends BaseActivity implements BaseView {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int OFFLINE_STATE = 2;
    private static final int ERROR_STATE = 3;
    private static final int EMPTY_STATE = 4;
    private int netType;

    private View mErrorView;
    private View mLoadingView;
    private View mOfflineView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int currentState = NORMAL_STATE;
    private TextView tvErrMsg;

    @Override
    protected void initView() {
        if (activity == null) {
            throw new IllegalStateException("Activity cannot be empty");
        }
        mNormalView = findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("There must be no mNormalView in the activity");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("The parent layout of mNormalView must belong to the viewgroup");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(activity, R.layout.view_loading, parent);
        View.inflate(activity, R.layout.view_offline, parent);
        View.inflate(activity, R.layout.view_error, parent);
        View.inflate(activity, R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mOfflineView = parent.findViewById(R.id.offline_group);
        mErrorView = parent.findViewById(R.id.error_group);
        mEmptyView = parent.findViewById(R.id.empty_group);
        tvErrMsg = parent.findViewById(R.id.tv_err_msg);
        Button retry = mOfflineView.findViewById(R.id.btn_retry);
        Button reLoad = mErrorView.findViewById(R.id.bt_reload);
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
            tvErrMsg.setText(err);
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
            default:
                break;
        }
    }

    @Override
    public void reload() {

    }
}
