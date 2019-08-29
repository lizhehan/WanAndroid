package com.lizhehan.wanandroid.ui.wxarticle.wxdetail;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.WxArticleDetailBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 微信公众号详情列表 presenter
 */

public class WxArticleDetailPresenter extends BasePresenter<WxArticleDetailContract.View> implements WxArticleDetailContract.Presenter {

    private WxArticleDetailContract.View view;
    private int wxId = -1;
    private int wxPage = -1;
    private boolean isRefresh = true;

    public WxArticleDetailPresenter(WxArticleDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        if (wxId != -1 && wxPage != -1) {
            getWxPublicListResult(wxId, 1);
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        if (wxId != -1 && wxPage != -1) {
            wxPage++;
            getWxPublicListResult(wxId, wxPage);
        }
    }

    @Override
    public void getWxPublicListResult(int id, int page) {
        this.wxId = id;
        this.wxPage = page;
        ApiStore.getInstance()
                .create(ApiService.class)
                .getWXDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<WxArticleDetailBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getWxPublicErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<WxArticleDetailBean> wxArticleDetailBaseResponse) {
                        if (wxArticleDetailBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getWxPublicErr(wxArticleDetailBaseResponse.getErrorMsg());
                        } else if (wxArticleDetailBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getWxPublicListOk(wxArticleDetailBaseResponse.getData(), isRefresh);
                        }
                    }

                });
    }
}
