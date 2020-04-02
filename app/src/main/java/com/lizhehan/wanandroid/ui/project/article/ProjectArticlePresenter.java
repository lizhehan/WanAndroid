package com.lizhehan.wanandroid.ui.project.article;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectArticlePresenter extends BasePresenter<ProjectArticleContract.View> implements ProjectArticleContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;
    private int cid;

    @Override
    public void getProjectArticleList(int page, int cid) {
        this.cid = cid;
        WanRetrofitService.getInstance()
                .create()
                .getProjectArticleList(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Article>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Article>> response) {
                        view.getProjectArticleListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getProjectArticleListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getProjectArticleList(0, cid);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getProjectArticleList(curPage, cid);
    }
}
