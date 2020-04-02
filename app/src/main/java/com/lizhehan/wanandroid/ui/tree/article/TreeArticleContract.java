package com.lizhehan.wanandroid.ui.tree.article;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;

import java.util.List;

public interface TreeArticleContract {
    interface View extends BaseContract.View {
        void getTreeArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getTreeArticleListError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTreeArticleList(int page, int cid);

        void refresh();

        void loadMore();
    }
}
