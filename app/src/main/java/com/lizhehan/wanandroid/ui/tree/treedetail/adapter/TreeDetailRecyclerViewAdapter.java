package com.lizhehan.wanandroid.ui.tree.treedetail.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.ArticleBean;

import java.util.List;


/**
 * 体系 二级 列表界面 item 适配器 （和 home adapter 类似）
 */

public class TreeDetailRecyclerViewAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public TreeDetailRecyclerViewAdapter(int layoutResId, @Nullable List<ArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean.DatasBean item) {
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
            String classifyName = item.getSuperChapterName() + " / " + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
//        helper.addOnClickListener(R.id.image_collect);
//        helper.setImageResource(R.id.image_collect, item.isCollect() ? R.drawable.ic_star : R.drawable.ic_star_border);
    }
}
