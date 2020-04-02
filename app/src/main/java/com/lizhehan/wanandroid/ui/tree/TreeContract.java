package com.lizhehan.wanandroid.ui.tree;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Chapter;

import java.util.List;

public interface TreeContract {
    interface View extends BaseContract.View {
        void getTreeSuccess(List<Chapter> chapterList);

        void getTreeError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTree();

        void refresh();
    }
}
