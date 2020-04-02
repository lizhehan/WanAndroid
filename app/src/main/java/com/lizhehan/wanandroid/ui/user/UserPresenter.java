package com.lizhehan.wanandroid.ui.user;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.UserInfo;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {
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
    public void logout() {
        WanRetrofitService.getInstance()
                .create()
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.logoutSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.logoutError(errorMsg);
                    }
                });
    }
}
