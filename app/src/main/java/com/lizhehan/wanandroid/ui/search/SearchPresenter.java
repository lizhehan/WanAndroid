package com.lizhehan.wanandroid.ui.search;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.Tool;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private int curPage;
    private String k;

    @Override
    public void getQueryArticleList(int page, String k, boolean isRefresh) {
        this.k = k;
        WanRetrofitService.getInstance()
                .create()
                .getQueryArticleList(page, k)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getQueryArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getQueryArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void getHotKey() {
        WanRetrofitService.getInstance()
                .create()
                .getHotKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Tool>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Tool>> response) {
                        view.getHotKeySuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getHotKeyError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        getQueryArticleList(0, k, true);
    }

    @Override
    public void loadMore() {
        getQueryArticleList(curPage, k, false);
    }
}
