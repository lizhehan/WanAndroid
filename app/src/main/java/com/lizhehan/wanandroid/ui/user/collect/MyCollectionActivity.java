package com.lizhehan.wanandroid.ui.user.collect;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.databinding.ActivityTabPagerBinding;
import com.lizhehan.wanandroid.ui.user.collect.article.ArticleFragment;
import com.lizhehan.wanandroid.ui.user.collect.website.WebsiteFragment;

public class MyCollectionActivity extends BaseActivity {

    private ActivityTabPagerBinding binding;

    @Override
    protected View getViewBindingRoot() {
        binding = ActivityTabPagerBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.my_collection));
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return new ArticleFragment();
                } else {
                    return new WebsiteFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText(getString(R.string.article));
                } else {
                    tab.setText(getString(R.string.website));
                }
            }
        }).attach();
    }

    @Override
    protected void initData() {

    }
}
