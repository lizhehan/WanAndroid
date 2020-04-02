package com.lizhehan.wanandroid.ui.project.article;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.ActivityTabPagerBinding;

import java.util.List;

public class ProjectActivity extends BaseActivity {

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
        getSupportActionBar().setTitle(getString(R.string.title_project_tree));
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        List<Chapter> chapterList = (List<Chapter>) getIntent().getSerializableExtra(Constants.CHAPTER);
        int childPosition = getIntent().getIntExtra(Constants.CHAPTER_CHILD, 0);
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                ProjectArticleFragment projectArticleFragment = new ProjectArticleFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.ID, chapterList.get(position).getId());
                projectArticleFragment.setArguments(args);
                return projectArticleFragment;
            }

            @Override
            public int getItemCount() {
                return chapterList.size();
            }
        });
        binding.viewPager2.setCurrentItem(childPosition, false);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(chapterList.get(position).getName());
            }
        }).attach();
    }
}
