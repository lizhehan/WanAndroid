package com.lizhehan.wanandroid.ui.collect;


import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.CollectBean;

/**
 * 我的收藏 契约类
 */

public class CollectContract {

    public interface View extends BaseView {
        /**
         * 获取我的收藏列表成功
         */
        void getCollectionListOK(CollectBean collectBean, boolean isRefresh);

        /**
         * 获取我的收藏列表失败
         */
        void getCollectionListErr(String err);

    }

    public interface Presenter extends BasePre<View> {

        void onRefresh();

        void onLoadMore();

        void getCollectionList(int page);
    }

}
