package com.lizhehan.wanandroid.ui.user;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.UserInfo;

public interface UserContract {
    interface View extends BaseContract.View {
        void getUserInfoSuccess(UserInfo userInfo);

        void getUserInfoError(String errorMsg);

        void logoutSuccess();

        void logoutError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getUserInfo();

        void logout();
    }
}
