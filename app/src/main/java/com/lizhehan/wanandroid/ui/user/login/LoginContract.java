package com.lizhehan.wanandroid.ui.user.login;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.User;

public interface LoginContract {
    interface View extends BaseContract.View {
        void loginSuccess(User user);

        void loginError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void login(String username, String password);
    }
}
