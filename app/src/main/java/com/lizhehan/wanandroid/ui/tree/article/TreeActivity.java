package com.lizhehan.wanandroid.ui.tree.article;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.ActivityTabPagerBinding;

public class TreeActivity extends BaseActivity {

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
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Chapter chapter = (Chapter) getIntent().getSerializableExtra(Constants.CHAPTER);
        int childPosition = getIntent().getIntExtra(Constants.CHAPTER_CHILD, 0);
        getSupportActionBar().setTitle(chapter.getName());
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                TreeArticleFragment treeArticleFragment = new TreeArticleFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.ID, chapter.getChildren().get(position).getId());
                treeArticleFragment.setArguments(args);
                return treeArticleFragment;
            }

            @Override
            public int getItemCount() {
                return chapter.getChildren().size();
            }
        });
        binding.viewPager2.setCurrentItem(childPosition, false);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(chapter.getChildren().get(position).getName());
            }
        }).attach();
    }
}
