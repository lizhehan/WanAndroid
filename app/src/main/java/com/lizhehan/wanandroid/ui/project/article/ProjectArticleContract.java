package com.lizhehan.wanandroid.ui.project.article;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;

import java.util.List;

public interface ProjectArticleContract {
    interface View extends BaseContract.View {
        void getProjectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getProjectArticleListError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getProjectArticleList(int page, int cid);

        void refresh();

        void loadMore();
    }
}
