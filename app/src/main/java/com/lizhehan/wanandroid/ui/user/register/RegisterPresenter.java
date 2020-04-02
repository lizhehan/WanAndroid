package com.lizhehan.wanandroid.ui.user.register;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    @Override
    public void register(String username, String password, String repassword) {
        WanRetrofitService.getInstance()
                .create()
                .register(username, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<User>>(view) {
                    @Override
                    public void onSuccess(WanResponse<User> response) {
                        view.registerSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.registerError(errorMsg);
                    }
                });
    }
}
