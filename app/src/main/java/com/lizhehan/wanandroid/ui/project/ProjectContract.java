package com.lizhehan.wanandroid.ui.project;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Chapter;

import java.util.List;

public interface ProjectContract {
    interface View extends BaseContract.View {
        void getLatestProjectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage);

        void getLatestProjectArticleListError(String errorMsg);

        void getProjectChaptersSuccess(List<Chapter> chapterList);

        void getProjectChaptersError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getLatestProjectArticleList(int page);

        void refresh();

        void loadMore();

        void getProjectChapters();
    }
}
