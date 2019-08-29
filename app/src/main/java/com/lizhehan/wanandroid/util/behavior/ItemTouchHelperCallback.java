package com.lizhehan.wanandroid.util.behavior;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lizhehan.wanandroid.interf.SwipedListener;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final int TYPE_NORMAL = 1;

    private SwipedListener moveAndSwipedListener;

    public ItemTouchHelperCallback(SwipedListener listener) {
        this.moveAndSwipedListener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == TYPE_NORMAL) {
            final int dragFlags = 0;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            return 0;
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
