package com.lizhehan.wanandroid.ui.tree.treedetail;


import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.TreeDetailBean;

/**
 * 体系二级 列表 契约类
 */

public class TreeDetailContract {

    public interface View extends BaseView {
        /**
         * 获取 体系 二级 数据成功
         *
         * @param treeDetailBean
         * @param isRefresh
         */
        void getSystemDetailListResultOK(TreeDetailBean treeDetailBean, boolean isRefresh);

        /**
         * 获取 数据失败
         *
         * @param info
         */
        void getSystemDetailListResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void autoRefresh();

        void loadMore();

        void getSystemDetailList(int page, int id);
    }

}
