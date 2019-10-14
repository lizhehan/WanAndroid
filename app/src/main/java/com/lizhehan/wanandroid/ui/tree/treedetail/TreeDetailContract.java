package com.lizhehan.wanandroid.ui.tree.treedetail;


import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.ArticleBean;

/**
 * 体系二级 列表 契约类
 */

public class TreeDetailContract {

    public interface View extends BaseView {
        /**
         * 获取 体系 二级 数据成功
         *
         * @param articleBean
         * @param isRefresh
         */
        void getTreeDetailResultOK(ArticleBean articleBean, boolean isRefresh);

        /**
         * 获取 数据失败
         *
         * @param info
         */
        void getTreeDetailResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void refresh();

        void loadMore();

        void getTreeDetailList(int page, int id);
    }
}
