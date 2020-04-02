package com.lizhehan.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Tool;

import org.jetbrains.annotations.NotNull;

public class ToolAdapter extends BaseQuickAdapter<Tool, BaseViewHolder> {
    public ToolAdapter() {
        super(R.layout.item_tool);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Tool tool) {
        baseViewHolder.setText(R.id.nameTextView, tool.getName());
        baseViewHolder.setText(R.id.linkTextView, tool.getLink());
    }
}
