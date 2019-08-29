package com.lizhehan.wanandroid.ui.project;

import com.lizhehan.wanandroid.base.BasePre;
import com.lizhehan.wanandroid.base.BaseView;
import com.lizhehan.wanandroid.data.bean.ProjectBean;

import java.util.List;

public class ProjectContract {

    public interface View extends BaseView {

        void getDemoResultOK(List<ProjectBean> demoBeans);

        void getDemoResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void getDemoTitleList();

    }

}
