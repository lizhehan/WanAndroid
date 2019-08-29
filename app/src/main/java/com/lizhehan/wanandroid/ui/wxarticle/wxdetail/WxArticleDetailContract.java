package com.lizhehan.wanandroid.ui.wxarticle.wxdetail;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.WxArticleDetailBean;

public class WxArticleDetailContract {

    interface View extends BaseView {

        void getWxPublicListOk(WxArticleDetailBean bean, boolean hasRefresh);

        void getWxPublicErr(String err);

    }

    interface Presenter extends BasePre<View> {

        void onRefresh();

        void onLoadMore();

        void getWxPublicListResult(int id, int page);

    }
}
