package com.lizhehan.wanandroid.ui.wechat.article;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;

import java.util.List;

public interface WXArticleContract {
    interface View extends BaseContract.View {
        void getWXArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getWXArticleListError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getWXArticleList(int page, int id);

        void refresh();

        void loadMore();
    }
}
