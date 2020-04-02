package com.lizhehan.wanandroid.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lizhehan.wanandroid.bean.Banner;
import com.lizhehan.wanandroid.widget.RoundImageView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageBannerAdapter extends BannerAdapter<Banner, ImageBannerAdapter.BannerViewHolder> {
    public ImageBannerAdapter(List<Banner> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        RoundImageView imageView = new RoundImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Banner data, int position, int size) {
        Glide.with(holder.itemView).load(data.getImagePath()).into(holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        BannerViewHolder(@NonNull ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }
}
