package com.lizhehan.wanandroid.ui.user.login;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Override
    public void login(String username, String password) {
        WanRetrofitService.getInstance()
                .create()
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<User>>(view) {
                    @Override
                    public void onSuccess(WanResponse<User> response) {
                        view.loginSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loginError(errorMsg);
                    }
                });
    }
}
