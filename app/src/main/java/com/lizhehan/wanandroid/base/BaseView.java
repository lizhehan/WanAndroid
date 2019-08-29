package com.lizhehan.wanandroid.base;

public interface BaseView {
    void showNormal();

    void showError(String err);

    void showLoading();

    void showOffline();

    void showEmpty();

    void reload();
}
