package com.lizhehan.wanandroid.ui.home;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Banner;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;

    @Override
    public void getBanner() {
        WanRetrofitService.getInstance()
                .create()
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Banner>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Banner>> response) {
                        view.getBannerSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getHomeArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void getHomeArticleList(int page) {
        WanRetrofitService.getInstance()
                .create()
                .getHomeArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getHomeArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getHomeArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getBanner();
        getHomeArticleList(0);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getHomeArticleList(curPage);
    }
}
