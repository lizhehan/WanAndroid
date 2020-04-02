package com.lizhehan.wanandroid.ui.user.coin;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Coin;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.UserInfo;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCoinPresenter extends BasePresenter<MyCoinContract.View> implements MyCoinContract.Presenter {

    private boolean isRefresh = true;
    private int curPage;

    @Override
    public void getUserInfo() {
        WanRetrofitService.getInstance()
                .create()
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<UserInfo>>(view) {
                    @Override
                    public void onSuccess(WanResponse<UserInfo> response) {
                        view.getUserInfoSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getUserInfoError(errorMsg);
                    }
                });
    }

    @Override
    public void getCoinList(int page) {
        WanRetrofitService.getInstance()
                .create()
                .getCoinList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Page<Coin>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Page<Coin>> response) {
                        view.getCoinListSuccess(response.getData().getDatas(), isRefresh, response.getData().getCurPage() == response.getData().getPageCount());
                        curPage = response.getData().getCurPage();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getCoinListError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        getCoinList(1);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getCoinList(++curPage);
    }
}
