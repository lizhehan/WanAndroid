package com.lizhehan.wanandroid.ui.tree.treedetail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lizhehan.wanandroid.ui.tree.treedetail.TreeDetailFragment;

import java.util.List;


/**
 * 体系 详细界面 viewpager 适配器
 */

public class TreeDetailFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private TreeDetailFragment fragment;
    private List<String> mTitles;

    public TreeDetailFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.list = list;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    /**
     * 获取当前显示的fragment
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        fragment = (TreeDetailFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public TreeDetailFragment getCurrentFragment() {
        return fragment;
    }
}
