package com.lizhehan.wanandroid.ui.wxarticle.wxdetail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lizhehan.wanandroid.ui.wxarticle.wxdetail.WxArticleDetailFragment;

import java.util.List;


/**
 * 微信fragment 适配器
 */

public class WxArticleDetailFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private WxArticleDetailFragment fragment;
    private List<String> mTitles;


    public WxArticleDetailFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
        } else {
            return fragments.size();
        }
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
        fragment = (WxArticleDetailFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public WxArticleDetailFragment getCurrentFragment() {
        return fragment;
    }

}
