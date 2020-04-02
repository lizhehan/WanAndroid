package com.lizhehan.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.ItemTreeBinding;

import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {

    private Context context;
    private List<Chapter> chapterList;
    private OnItemClickListener onItemClickListener;
    private OnItemChildClickListener onItemChildClickListener;

    public TreeAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemClickListener) {
        this.onItemChildClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tree, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.binding.nameTextView.setText(chapter.getName());
        holder.binding.chipGroup.removeAllViews();
        for (int i = 0; i < chapter.getChildren().size(); i++) {
            Chip chip = new Chip(context);
            chip.setCheckable(true);
            chip.setId(i);
            chip.setText(chapter.getChildren().get(i).getName());
            holder.binding.chipGroup.addView(chip);
        }
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
            }
        });
        holder.binding.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (onItemChildClickListener != null && checkedId != -1) {
                    group.clearCheck();
                    onItemChildClickListener.onClick(position, checkedId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnItemChildClickListener {
        void onClick(int position, int childPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTreeBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTreeBinding.bind(itemView);
        }
    }
}
