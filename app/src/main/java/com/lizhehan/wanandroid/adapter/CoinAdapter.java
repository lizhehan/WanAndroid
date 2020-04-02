package com.lizhehan.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Coin;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;

public class CoinAdapter extends BaseQuickAdapter<Coin, BaseViewHolder> implements LoadMoreModule {
    public CoinAdapter() {
        super(R.layout.item_coin);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Coin coin) {
        baseViewHolder.setText(R.id.descTextView, coin.getReason() + coin.getDesc().split(",")[1].replaceAll("\\s+", ""));
        //baseViewHolder.setText(R.id.dateTextView, coin.getDesc().substring(0, 19));
        baseViewHolder.setText(R.id.dateTextView, DateFormat.getDateTimeInstance().format(coin.getDate()));
        baseViewHolder.setText(R.id.coinCountTextView, String.valueOf(coin.getCoinCount()));
    }
}
