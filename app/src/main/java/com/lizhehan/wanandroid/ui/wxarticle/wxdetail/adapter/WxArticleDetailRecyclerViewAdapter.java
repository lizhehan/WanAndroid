package com.lizhehan.wanandroid.ui.wxarticle.wxdetail.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.WxArticleDetailBean;

import java.util.List;

/**
 * 微信公众号文章 adapter
 */

public class WxArticleDetailRecyclerViewAdapter extends BaseQuickAdapter<WxArticleDetailBean.DatasBean, BaseViewHolder> {

    public WxArticleDetailRecyclerViewAdapter(int layoutResId, @Nullable List<WxArticleDetailBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxArticleDetailBean.DatasBean item) {
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
            helper.setText(R.id.tv_type, item.getSuperChapterName());
        }
//        helper.setImageResource(R.id.image_collect, item.isCollect() ? R.drawable.ic_star : R.drawable.ic_star_border);

    }
}
