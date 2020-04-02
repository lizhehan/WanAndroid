package com.lizhehan.wanandroid.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Article;

import org.jetbrains.annotations.NotNull;

public class TreeArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> implements LoadMoreModule {
    public TreeArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Article article) {
        baseViewHolder.getView(R.id.tagTextView).setVisibility(View.GONE);
        for (Article.Tags tags : article.getTags()) {
            baseViewHolder.getView(R.id.tagTextView).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tagTextView, tags.getName());
            baseViewHolder.setBackgroundResource(R.id.tagTextView, R.drawable.shape_tag);
        }
        if (TextUtils.isEmpty(article.getAuthor())) {
            baseViewHolder.setText(R.id.authorTextView, getContext().getString(R.string.share_user) + article.getShareUser());
        } else {
            baseViewHolder.setText(R.id.authorTextView, getContext().getString(R.string.author) + article.getAuthor());
        }
        baseViewHolder.setText(R.id.dateTextView, article.getNiceDate().split("\\s+")[0]);
        baseViewHolder.setText(R.id.titleTextView, article.getTitle());
        baseViewHolder.getView(R.id.chapterTextView).setVisibility(View.GONE);
    }
}
