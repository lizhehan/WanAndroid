package com.lizhehan.wanandroid.ui.wxarticle.wxdetail;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.ArticleBean;

public class WxArticleDetailContract {

    interface View extends BaseView {

        void getWxArticleDetailOk(ArticleBean articleBean, boolean hasRefresh);

        void getWxArticleDetailErr(String err);
    }

    interface Presenter extends BasePre<View> {

        void refresh();

        void onLoadMore();

        void getWxArticleDetailResult(int id, int page);
    }
}
