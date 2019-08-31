package com.lizhehan.wanandroid.ui.project;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.ProjectBean;
import com.lizhehan.wanandroid.ui.project.projectdetail.ProjectDetailFragment;
import com.lizhehan.wanandroid.ui.project.projectdetail.adapter.ProjectDetailFragmentAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseFragment implements ProjectContract.View {

    @BindView(R.id.tab_layout_project)
    TabLayout projectTabLayout;
    @BindView(R.id.view_pager_project)
    ViewPager projectViewPager;

    private List<ProjectBean> demoBeanList;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private ProjectPresenter projectPresenter;
    private ProjectDetailFragmentAdapter adapter;

    public static ProjectFragment getInstance() {
        return new ProjectFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initData() {
        demoBeanList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles = new LinkedList<>();
        projectPresenter = new ProjectPresenter(this);
        projectPresenter.getProjectDetail();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void getProjectResultOK(List<ProjectBean> demoBeans) {
        demoBeanList.clear();
        fragmentList.clear();
        demoBeanList.addAll(demoBeans);
        if (demoBeanList.size() > 0) {
            for (ProjectBean projectBean : demoBeanList) {
                fragmentList.add(ProjectDetailFragment.getInstance(projectBean.getId()));
                titles.add(projectBean.getName());
            }
            adapter = new ProjectDetailFragmentAdapter(getChildFragmentManager(), fragmentList, titles);
            projectViewPager.setOffscreenPageLimit(2);
            projectViewPager.setAdapter(adapter);
            projectTabLayout.setupWithViewPager(projectViewPager);
            adapter.notifyDataSetChanged();
        }
        showNormal();
    }

    @Override
    public void getProjectResultErr(String info) {
        showError(info);
    }

    @Override
    public void reload() {
        showLoading();
        projectPresenter.getProjectDetail();
    }

    /**
     * 查找 子 fragment 回到顶部
     */
    public void scrollChildToTop() {
        if (adapter != null) {
            ProjectDetailFragment currentFragment = adapter.getCurrentFragment();
            currentFragment.scrollToTop();
        }
    }
}
