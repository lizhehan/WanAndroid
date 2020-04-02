package com.lizhehan.wanandroid.ui.user.collect.article;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;

import java.util.List;

public interface ArticleContract {
    interface View extends BaseContract.View {
        void getCollectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getCollectArticleListError(String errorMsg);

        void cancelCollectArticleSuccess(int position);

        void cancelCollectArticleError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getCollectArticleList(int page);

        void cancelCollectArticle(int id, int originId, int position);

        void refresh();

        void loadMore();
    }
}
