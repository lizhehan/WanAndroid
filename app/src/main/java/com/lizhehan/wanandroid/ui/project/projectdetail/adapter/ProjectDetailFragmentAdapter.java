package com.lizhehan.wanandroid.ui.project.projectdetail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lizhehan.wanandroid.ui.project.projectdetail.ProjectDetailFragment;

import java.util.List;


/**
 * 类似 体系界面  适配器
 */

public class ProjectDetailFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    ProjectDetailFragment fragment;
    private List<String> mTitles;

    public ProjectDetailFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null) {
            return 0;
        }
        return fragments.size();
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
        fragment = (ProjectDetailFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public ProjectDetailFragment getCurrentFragment() {
        return fragment;
    }
}
