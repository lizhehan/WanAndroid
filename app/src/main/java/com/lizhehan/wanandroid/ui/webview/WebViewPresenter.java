package com.lizhehan.wanandroid.ui.webview;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页详细界面 presenter 层
 */

public class WebViewPresenter extends BasePresenter<WebViewContract.view> implements WebViewContract.presenter {

    private WebViewContract.view view;

    public WebViewPresenter(WebViewContract.view view) {
        this.view = view;
    }

    /**
     * 收藏文章
     *
     * @param id
     */
    @Override
    public void collectArticle(int id) {
        ApiStore.getInstance()
                .create(ApiService.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        view.collectArticleErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.collectArticleErr(baseResponse.getErrorMsg());
                        } else if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.collectArticleOK((String) baseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 取消收藏文章
     *
     * @param id
     */
    @Override
    public void cancelCollectArticle(int id) {
        ApiStore.getInstance()
                .create(ApiService.class)
                .cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        view.cancelCollectArticleErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.cancelCollectArticleErr(baseResponse.getErrorMsg());
                        } else if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.cancelCollectArticleOK((String) baseResponse.getData());
                        }
                    }
                });
    }
}