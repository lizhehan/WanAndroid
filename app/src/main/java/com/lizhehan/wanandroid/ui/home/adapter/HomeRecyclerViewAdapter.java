package com.lizhehan.wanandroid.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.ArticleBean;

import java.util.List;

/**
 * rv 适配器
 */

public class HomeRecyclerViewAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public HomeRecyclerViewAdapter(int layoutResId, @Nullable List<ArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean.DatasBean item) {
        helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getSuperChapterName() + "・" + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        if (item.getSuperChapterName().contains(mContext.getString(R.string.open_source_project))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.project));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.google_green));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_green);
        } else if (item.getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.hot));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.google_red));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_red);
        } else if (item.getSuperChapterName().contains(mContext.getString(R.string.questions_and_answers))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.questions_and_answers));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.google_green));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_green);
        } else if (item.getSuperChapterName().contains(mContext.getString(R.string.official_accounts))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.official_accounts));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.google_green));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_green);
        }
//        helper.addOnClickListener(R.id.tv_type);
//        helper.addOnClickListener(R.id.image_collect);
//        helper.setImageResource(R.id.image_collect, item.isCollect() ? R.drawable.ic_star : R.drawable.ic_star_border);
    }
}
