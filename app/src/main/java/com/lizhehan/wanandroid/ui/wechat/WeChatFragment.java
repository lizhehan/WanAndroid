package com.lizhehan.wanandroid.ui.wechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.FragmentTabPagerBinding;
import com.lizhehan.wanandroid.ui.wechat.article.WXArticleFragment;

import java.util.List;

public class WeChatFragment extends BaseFragment<WeChatPresenter> implements WeChatContract.View {

    private FragmentTabPagerBinding binding;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentTabPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter = new WeChatPresenter();
        presenter.attachView(this);
        presenter.getWXChapters();
    }

    @Override
    public void getWXChaptersSuccess(List<Chapter> chapterList) {
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                WXArticleFragment wxArticleFragment = new WXArticleFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.ID, chapterList.get(position).getId());
                wxArticleFragment.setArguments(args);
                return wxArticleFragment;
            }

            @Override
            public int getItemCount() {
                return chapterList.size();
            }
        });
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(chapterList.get(position).getName());
            }
        }).attach();
    }

    @Override
    public void getWXChaptersError(String errorMsg) {

    }

    public void scrollChildToTop() {

    }
}
