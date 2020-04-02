package com.lizhehan.wanandroid.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Article;
import com.youth.banner.Banner;

import org.jetbrains.annotations.NotNull;

public class HomeAdapter extends BaseQuickAdapter<Article, BaseViewHolder> implements LoadMoreModule {
    public HomeAdapter() {
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
        baseViewHolder.setText(R.id.chapterTextView, article.getSuperChapterName() + "・" + article.getChapterName());
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.getAdapterPosition() == 0) {
            if (getHeaderLayoutCount() > 0) {
                Banner banner = (Banner) getHeaderLayout().getChildAt(0);
                banner.stop();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() == 0) {
            if (getHeaderLayoutCount() > 0) {
                Banner banner = (Banner) getHeaderLayout().getChildAt(0);
                banner.start();
            }
        }
    }
}
