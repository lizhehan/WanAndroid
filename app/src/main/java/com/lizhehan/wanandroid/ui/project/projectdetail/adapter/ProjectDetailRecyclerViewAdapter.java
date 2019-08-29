package com.lizhehan.wanandroid.ui.project.projectdetail.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.ProjectDetailBean;

import java.util.List;


/**
 * 项目 内容列表适配器
 */

public class ProjectDetailRecyclerViewAdapter extends BaseQuickAdapter<ProjectDetailBean.DatasBean, BaseViewHolder> {

    public ProjectDetailRecyclerViewAdapter(int layoutResId, @Nullable List<ProjectDetailBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProjectDetailBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDesc())) {
            helper.setText(R.id.tv_content, item.getDesc());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.image_simple));
    }
}
