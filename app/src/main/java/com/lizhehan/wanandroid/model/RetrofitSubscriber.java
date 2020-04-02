package com.lizhehan.wanandroid.model;

import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.WanResponse;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RetrofitSubscriber<T> implements Observer<T> {
    private WeakReference<BaseContract.View> view;

    public RetrofitSubscriber(BaseContract.View view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void onSubscribe(Disposable d) {
        view.get().addSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof WanResponse) {
            if (((WanResponse) t).getErrorCode() == Constants.ERROR_CODE_SUCCESS) {
                onSuccess(t);
            } else if (((WanResponse) t).getErrorCode() == Constants.ERROR_CODE_LOGIN_INVALID) {
                view.get().login();
                onError(((WanResponse) t).getErrorMsg());
            } else {
                onError(((WanResponse) t).getErrorMsg());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T response);

    public abstract void onError(String errorMsg);
}
