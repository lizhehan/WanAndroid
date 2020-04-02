package com.lizhehan.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Article;

import org.jetbrains.annotations.NotNull;

public class WXArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> implements LoadMoreModule {
    public WXArticleAdapter() {
        super(R.layout.item_wx_article);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Article article) {
        baseViewHolder.setText(R.id.dateTextView, article.getNiceDate().split("\\s+")[0]);
        baseViewHolder.setText(R.id.titleTextView, article.getTitle());
    }
}
