package com.lizhehan.wanandroid.ui.collect;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.CollectBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的收藏列表 presenter 层
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    private CollectContract.View view;
    private int currentPage;
    private boolean hasRefresh = true;

    public CollectPresenter(CollectContract.View view) {
        this.view = view;
    }

    @Override
    public void onRefresh() {
        hasRefresh = true;
        getCollectionList(0);
    }

    @Override
    public void onLoadMore() {
        hasRefresh = false;
        currentPage++;
        getCollectionList(currentPage);
    }

    @Override
    public void getCollectionList(int page) {
        this.currentPage = page;
        ApiStore.getInstance()
                .create(ApiService.class)
                .getCollectionList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<CollectBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getCollectionListErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CollectBean> collectBaseResponse) {
                        if (collectBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getCollectionListOK(collectBaseResponse.getData(), hasRefresh);
                        } else if (collectBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getCollectionListErr(collectBaseResponse.getErrorMsg());
                        } else if (collectBaseResponse.getErrorCode() == ConstantUtil.REQUEST_NOT_LOGGED_IN) {

                        }
                    }
                });
    }
}
