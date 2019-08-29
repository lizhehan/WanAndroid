package com.lizhehan.wanandroid.base;

public interface BasePre<T extends BaseView> {
    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}
