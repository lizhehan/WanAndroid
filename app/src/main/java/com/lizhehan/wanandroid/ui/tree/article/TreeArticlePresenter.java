package com.lizhehan.wanandroid.ui.tree.article;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TreeArticlePresenter extends BasePresenter<TreeArticleContract.View> implements TreeArticleContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;
    private int cid;

    @Override
    public void getTreeArticleList(int page, int cid) {
        this.cid = cid;
        WanRetrofitService.getInstance()
                .create()
                .getTreeArticleList(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getTreeArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getTreeArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getTreeArticleList(0, cid);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getTreeArticleList(curPage, cid);
    }
}
