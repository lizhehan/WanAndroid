package com.lizhehan.wanandroid.ui.web;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {
    @Override
    public void collectArticle(int id) {
        WanRetrofitService.getInstance()
                .create()
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.collectArticleSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.collectArticleError(errorMsg);
                    }
                });
    }

    @Override
    public void cancelCollectArticle(int id) {
        WanRetrofitService.getInstance()
                .create()
                .uncollectArticleWithOriginId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.cancelCollectArticleSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.cancelCollectArticleError(errorMsg);
                    }
                });
    }

    @Override
    public void addTool(String name, String link) {
        WanRetrofitService.getInstance()
                .create()
                .addTool(name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.addToolSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.addToolError(errorMsg);
                    }
                });
    }
}
