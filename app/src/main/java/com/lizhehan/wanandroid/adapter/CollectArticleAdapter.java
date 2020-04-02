package com.lizhehan.wanandroid.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Article;

import org.jetbrains.annotations.NotNull;

public class CollectArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> implements LoadMoreModule {
    public CollectArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Article article) {
        if (TextUtils.isEmpty(article.getAuthor())) {
            baseViewHolder.setText(R.id.authorTextView, getContext().getString(R.string.anonymous));
        } else {
            baseViewHolder.setText(R.id.authorTextView, article.getAuthor());
        }
        baseViewHolder.setText(R.id.dateTextView, article.getNiceDate().split("\\s+")[0]);
        baseViewHolder.setText(R.id.titleTextView, article.getTitle());
        baseViewHolder.setText(R.id.chapterTextView, article.getChapterName());
    }
}
