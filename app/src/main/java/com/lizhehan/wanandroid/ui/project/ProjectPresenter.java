package com.lizhehan.wanandroid.ui.project;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;

    @Override
    public void getLatestProjectArticleList(int page) {
        WanRetrofitService.getInstance()
                .create()
                .getLatestProjectArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getLatestProjectArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getLatestProjectArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getLatestProjectArticleList(0);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getLatestProjectArticleList(curPage);
    }

    @Override
    public void getProjectChapters() {
        WanRetrofitService.getInstance()
                .create()
                .getProjectChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Chapter>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Chapter>> response) {
                        view.getProjectChaptersSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getProjectChaptersError(errorMsg);
                    }
                });
    }
}
