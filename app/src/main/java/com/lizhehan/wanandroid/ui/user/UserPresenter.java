package com.lizhehan.wanandroid.ui.user;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {

    UserContract.View view;

    public UserPresenter(UserContract.View view) {
        this.view = view;
    }

    @Override
    public void login(String name, String password) {
        ApiStore.getInstance()
                .createLogin(ApiService.class)
                .login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UserBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() != null) {
                            view.loginErr(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UserBean> userBaseResponse) {
                        if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.loginOk(userBaseResponse.getData());
                        } else if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.loginErr(userBaseResponse.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void register(String name, String password, String rePassWord) {
        ApiStore.getInstance()
                .create(ApiService.class)
                .register(name, password, rePassWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UserBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.registerErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UserBean> userBaseResponse) {
                        if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.registerOk(userBaseResponse.getData());
                        } else if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.registerErr(userBaseResponse.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void logout() {
        ApiStore.getInstance()
                .create(ApiService.class)
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        view.logoutErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.logoutOk((String) baseResponse.getData());
                        } else if (baseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.logoutErr(baseResponse.getErrorMsg());
                        }
                    }
                });
    }
}