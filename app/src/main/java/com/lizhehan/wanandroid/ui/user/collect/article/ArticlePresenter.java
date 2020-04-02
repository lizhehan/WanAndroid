package com.lizhehan.wanandroid.ui.user.collect.article;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;

    @Override
    public void getCollectArticleList(int page) {
        WanRetrofitService.getInstance()
                .create()
                .getCollectArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getCollectArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getCollectArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void cancelCollectArticle(int id, int originId, int position) {
        WanRetrofitService.getInstance()
                .create()
                .uncollectArticle(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.cancelCollectArticleSuccess(position);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.cancelCollectArticleError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getCollectArticleList(0);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getCollectArticleList(curPage);
    }
}
