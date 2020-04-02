package com.lizhehan.wanandroid.ui.user.collect.website;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Tool;

import java.util.List;

public interface WebsiteContract {
    interface View extends BaseContract.View {
        void getToolsSuccess(List<Tool> tools);

        void getToolsError(String errorMsg);

        void updateToolSuccess(Tool tool, int position);

        void updateToolError(String errorMsg);

        void deleteToolSuccess(int position);

        void deleteToolError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTools();

        void updateTool(int id, String name, String link, int position);

        void deleteTool(int id, int position);
    }
}
