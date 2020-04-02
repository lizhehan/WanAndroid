package com.lizhehan.wanandroid.ui.user.register;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.User;

public interface RegisterContract {
    interface View extends BaseContract.View {
        void registerSuccess(User user);

        void registerError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void register(String username, String password, String repassword);
    }
}
