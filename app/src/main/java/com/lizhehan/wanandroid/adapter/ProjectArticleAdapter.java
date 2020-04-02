package com.lizhehan.wanandroid.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Article;

import org.jetbrains.annotations.NotNull;

public class ProjectArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> implements LoadMoreModule {
    public ProjectArticleAdapter() {
        super(R.layout.item_project_article);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Article article) {
        baseViewHolder.setText(R.id.authorTextView, article.getAuthor());
        baseViewHolder.setText(R.id.dateTextView, article.getNiceDate().split("\\s+")[0]);
        baseViewHolder.setText(R.id.titleTextView, article.getTitle());
        baseViewHolder.setText(R.id.descTextView, article.getDesc());
        Glide.with(getContext())
                .load(article.getEnvelopePic())
                .into((ImageView) baseViewHolder.getView(R.id.envelopePicImageView));
    }
}
