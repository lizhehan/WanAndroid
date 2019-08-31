package com.lizhehan.wanandroid.ui.wxarticle;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.WxArticleBean;

import java.util.List;

/**
 * 微信公众号界面
 */

public class WxArticleContract {

    public interface View extends BaseView {

        void getWxArticleResultOK(List<WxArticleBean> demoBeans);

        void getWxArticleResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void getWxArticle();
    }
}
