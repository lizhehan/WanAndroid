package com.lizhehan.wanandroid.ui.wechat.article;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WXArticlePresenter extends BasePresenter<WXArticleContract.View> implements WXArticleContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;
    private int id;

    @Override
    public void getWXArticleList(int page, int id) {
        this.id = id;
        WanRetrofitService.getInstance()
                .create()
                .getWXArticleList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getWXArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getWXArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getWXArticleList(0, id);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getWXArticleList(curPage, id);
    }
}
