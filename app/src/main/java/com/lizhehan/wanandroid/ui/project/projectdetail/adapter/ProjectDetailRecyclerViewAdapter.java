package com.lizhehan.wanandroid.ui.project.projectdetail.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        helper.getView(R.id.progress_bar_loading).setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(item.getEnvelopePic())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        helper.getView(R.id.progress_bar_loading).setVisibility(View.INVISIBLE);
                        helper.getView(R.id.tv_image_failed_to_load).setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        helper.getView(R.id.progress_bar_loading).setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into((ImageView) helper.getView(R.id.image_simple));
    }
}
