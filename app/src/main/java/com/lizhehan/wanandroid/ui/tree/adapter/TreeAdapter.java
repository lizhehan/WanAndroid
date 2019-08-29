package com.lizhehan.wanandroid.ui.tree.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.TreeBean;

import java.util.List;


/**
 * 体系界面列表 适配器
 */

public class TreeAdapter extends BaseQuickAdapter<TreeBean, BaseViewHolder> {

    public TreeAdapter(int layoutResId, @Nullable List<TreeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeBean item) {
        helper.setText(R.id.tv_knowledge_title, item.getName());
        StringBuilder sb = new StringBuilder();
        for (TreeBean.ChildrenBean childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName()).append("    ");
        }
        helper.setText(R.id.tv_knowledge_content, sb.toString());
    }
}
