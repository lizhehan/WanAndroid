package com.lizhehan.wanandroid.ui.tree;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.TreeBean;

import java.util.List;


/**
 * 接口契约类
 */

public class TreeContract {

    interface View extends BaseView {

        void getTreeOk(List<TreeBean> dataBean);

        void getTreeErr(String info);
    }

    interface Presenter extends BasePre<View> {

        void refresh();

        void getTree();
    }
}
