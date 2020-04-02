package com.lizhehan.wanandroid.ui.wechat;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Chapter;

import java.util.List;

public interface WeChatContract {
    interface View extends BaseContract.View {
        void getWXChaptersSuccess(List<Chapter> chapterList);

        void getWXChaptersError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getWXChapters();
    }
}
