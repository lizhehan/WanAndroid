package com.lizhehan.wanandroid.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.lizhehan.wanandroid.data.bean.CollectBean;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {
    private List<CollectBean.DatasBean> mOldDatas;
    private List<CollectBean.DatasBean> mNewDatas;

    public DiffCallBack(List<CollectBean.DatasBean> oldDatas, List<CollectBean.DatasBean> newDatas) {
        mOldDatas = oldDatas;
        mNewDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getTitle().equals(mNewDatas.get(newItemPosition).getTitle());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CollectBean.DatasBean oldBean = mOldDatas.get(oldItemPosition);
        CollectBean.DatasBean newBean = mNewDatas.get(newItemPosition);
        if (!oldBean.getTitle().equals(newBean.getTitle())) {
            return false;
        }
        if (!oldBean.getAuthor().equals(newBean.getAuthor())) {
            return false;
        }
        if (!oldBean.getNiceDate().equals(newBean.getNiceDate())) {
            return false;
        }
        return oldBean.getChapterName().equals(newBean.getChapterName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        CollectBean.DatasBean oldBean = mOldDatas.get(oldItemPosition);
        CollectBean.DatasBean newBean = mNewDatas.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!oldBean.getTitle().equals(newBean.getTitle())) {
            payload.putString("KEY_TITLE", newBean.getTitle());
        }
        if (!oldBean.getAuthor().equals(newBean.getAuthor())) {
            payload.putString("KEY_AUTHOR", newBean.getAuthor());
        }
        if (!oldBean.getNiceDate().equals(newBean.getNiceDate())) {
            payload.putString("KEY_NICE_DATE", newBean.getNiceDate());
        }
        if (!oldBean.getChapterName().equals(newBean.getChapterName())) {
            payload.putString("KEY_CHAPTER_NAME", newBean.getChapterName());
        }
        if (payload.size() == 0) {
            return null;
        }
        return payload;
    }
}
