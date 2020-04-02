package com.lizhehan.wanandroid.ui.home;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Banner;

import java.util.List;

public interface HomeContract {
    interface View extends BaseContract.View {
        void getBannerSuccess(List<Banner> bannerList);

        void getBannerError(String errorMsg);

        void getHomeArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getHomeArticleListError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getBanner();

        void getHomeArticleList(int page);

        void refresh();

        void loadMore();
    }
}
