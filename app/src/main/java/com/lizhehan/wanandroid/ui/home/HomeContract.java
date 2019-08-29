package com.lizhehan.wanandroid.ui.home;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.BannerBean;
import com.lizhehan.wanandroid.data.bean.HomeArticleBean;
import com.lizhehan.wanandroid.data.bean.UserBean;

import java.util.List;


/**
 * 首页 契约类
 */

public class HomeContract {

    public interface View extends BaseView {

        void getHomepageListOk(HomeArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

        void loginOk(UserBean userBean);

        void loginErr(String err);
    }

    public interface Per extends BasePre<View> {
        /**
         * 刷新 列表
         */
        void autoRefresh();

        /**
         * 加載更多
         */
        void loadMore();

        /**
         * 获取 轮播信息
         */
        void getBanner();

        /**
         * 获取 首页 页数数据
         *
         * @param page
         */
        void getHomepageListData(int page);

        /**
         * 帐号 登录
         */
        void loginUser(String username, String password);
    }

}
