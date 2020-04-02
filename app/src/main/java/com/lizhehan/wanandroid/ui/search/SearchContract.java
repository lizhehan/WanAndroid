package com.lizhehan.wanandroid.ui.search;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Tool;

import java.util.List;

public interface SearchContract {
    interface View extends BaseContract.View {
        void getQueryArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getQueryArticleListError(String errorMsg);

        void getHotKeySuccess(List<Tool> toolList);

        void getHotKeyError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getQueryArticleList(int page, String k, boolean isRefresh);

        void getHotKey();

        void refresh();

        void loadMore();
    }
}
