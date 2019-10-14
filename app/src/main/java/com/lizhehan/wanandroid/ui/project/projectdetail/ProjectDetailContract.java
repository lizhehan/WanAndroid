package com.lizhehan.wanandroid.ui.project.projectdetail;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.ArticleBean;

public class ProjectDetailContract {

    public interface View extends BaseView {

        /**
         * 获取 项目列表成功
         *
         * @param articleBean
         * @param isRefresh
         */
        void getProjectDetailOK(ArticleBean articleBean, boolean isRefresh);

        /**
         * 获取 项目失败
         *
         * @param err
         */
        void getProjectDetailErr(String err);
    }

    public interface Presenter extends BasePre<View> {

        /**
         * 获取 项目列表
         *
         * @param page
         * @param id
         */
        void getProjectDetail(int page, int id);

        /**
         * 刷新
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadMore();
    }
}
