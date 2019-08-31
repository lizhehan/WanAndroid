package com.lizhehan.wanandroid.ui.wxarticle;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.WxArticleBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 微信公众号 presenter
 */

public class WxArticlePresenter extends BasePresenter<WxArticleContract.View> implements WxArticleContract.Presenter {

    private WxArticleContract.View view;

    public WxArticlePresenter(WxArticleContract.View view) {
        this.view = view;
    }

    @Override
    public void getWxArticle() {
        ApiStore.getInstance()
                .create(ApiService.class)
                .getWXList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<WxArticleBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<List<WxArticleBean>> wxArticleBaseResponse) {
                        if (wxArticleBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getWxArticleResultOK(wxArticleBaseResponse.data);
                        } else if (wxArticleBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getWxArticleResultErr(wxArticleBaseResponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getWxArticleResultErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
