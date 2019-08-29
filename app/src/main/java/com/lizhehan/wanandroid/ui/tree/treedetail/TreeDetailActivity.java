package com.lizhehan.wanandroid.ui.tree.treedetail;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.data.bean.TreeBean;
import com.lizhehan.wanandroid.ui.tree.treedetail.adapter.TreeDetailFragmentAdapter;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TreeDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_tree_detail)
    Toolbar treeToolbar;
    @BindView(R.id.tab_layout_tree_detai)
    TabLayout treeTabLayout;
    @BindView(R.id.view_pager_tree_detail)
    ViewPager treeViewpager;
    @BindView(R.id.fab_tree_detail)
    FloatingActionButton treeFab;

    private List<String> titles;
    private List<Fragment> fragments;
    private TreeDetailFragmentAdapter adapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_tree_detail;
    }

    @Override
    protected void initView() {
        treeToolbar.setTitle("");
        setSupportActionBar(treeToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        treeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
//        adapter = new TreeDetailFragmentAdapter(getSupportFragmentManager(), fragments);
        getSystemBundleData();
    }

    @Override
    protected void checkNet() {

    }

    /**
     * 获取体系界面传过来的参数
     */
    private void getSystemBundleData() {
        TreeBean treeBean = (TreeBean) getIntent().getSerializableExtra(ConstantUtil.TREE);
        if (treeBean != null) {
            fragments.clear();
            treeToolbar.setTitle(treeBean.getName());
            for (TreeBean.ChildrenBean childrenBean : treeBean.getChildren()) {
//                treeTabLayout.addTab(treeTabLayout.newTab().setText(childrenBean.getName()));
                titles.add(childrenBean.getName());
                fragments.add(TreeDetailFragment.getInstance(childrenBean.getId()));
            }
        }
        adapter = new TreeDetailFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        treeViewpager.setOffscreenPageLimit(2);
        treeViewpager.setAdapter(adapter);
//        systemTabLayout.setViewPager(systemViewpager, titles.toArray(new String[titles.size()]));
        treeTabLayout.setupWithViewPager(treeViewpager);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_tree_detail)
    public void onViewClicked() {
        TreeDetailFragment fragment = adapter.getCurrentFragment();
        fragment.scrollToTop();
    }
}
