package com.lizhehan.wanandroid.ui.webview;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;

/**
 * 首页详情界面
 */

public class WebViewContract {

    public interface view extends BaseView {

        /**
         * 收藏 成功 失败
         *
         * @param info
         */
        void collectArticleOK(String info);

        void collectArticleErr(String info);

        /**
         * 取消收藏 成功 失败
         *
         * @param info
         */
        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface presenter extends BasePre<view> {

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }

}
