package com.lizhehan.wanandroid.base;

import io.reactivex.disposables.Disposable;

public interface BaseContract {
    interface View {
        void login();

        void addSubscribe(Disposable disposable);
    }

    interface Presenter<V extends BaseContract.View> {
        void attachView(V view);

        void detachView();
    }
}
