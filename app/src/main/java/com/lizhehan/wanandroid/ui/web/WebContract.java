package com.lizhehan.wanandroid.ui.web;

import com.lizhehan.wanandroid.base.BaseContract;

public interface WebContract {
    interface View extends BaseContract.View {
        void collectArticleSuccess();

        void collectArticleError(String errorMsg);

        void cancelCollectArticleSuccess();

        void cancelCollectArticleError(String errorMsg);

        void addToolSuccess();

        void addToolError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void collectArticle(int id);

        void cancelCollectArticle(int id);

        void addTool(String name, String link);
    }
}
