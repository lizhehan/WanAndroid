package com.lizhehan.wanandroid.ui.user;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.UserBean;

/**
 * login 登陆
 */

public class UserContract {

    public interface View extends BaseView {

        void loginOk(UserBean userBean);

        void loginErr(String info);

        void registerOk(UserBean userBean);

        void registerErr(String info);

        void logoutOk(String info);

        void logoutErr(String info);

    }

    public interface Presenter extends BasePre<View> {

        void login(String name, String password);

        void register(String name, String password, String rePassWord);

        void logout();

    }
}
